package net.pneumono.pneumonocore.datagen;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.pneumono.pneumonocore.mixin.client.NativeImageInvoker;
import org.lwjgl.stb.STBImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

//? if >=1.20.5 {
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;
//?}

@SuppressWarnings("unused")
public abstract class TextureProvider implements DataProvider {
    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public TextureProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        this.packOutput = packOutput;
        this.registries = registries;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<PackResources> packs = new ArrayList<>();
        packs.add(new VanillaPackResourcesBuilder()
                .exposeNamespace("minecraft")
                .pushJarResources()
                .build(
                        //? if >=1.20.5
                        makePackInfo("vanilla_textures")
                )
        );

        packs.add(new PathPackResources(
                makePackInfo("non_generated_textures"),
                this.packOutput.getOutputFolder().resolve("../../../../../src/main/resources")
                //? if <1.20.5
                //, true
        ));

        packs.add(new PathPackResources(
                makePackInfo("generated_textures"),
                this.packOutput.getOutputFolder()
                //? if <1.20.5
                //, true
        ));

        try (MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, packs)) {
            BiConsumer<NativeImage, Identifier> output = (image, id) -> {
                Path path = this.packOutput.getOutputFolder()
                        .resolve("assets")
                        .resolve(id.getNamespace())
                        .resolve(id.getPath());

                try {
                    Files.createDirectories(path.getParent());
                    image.writeToFile(path);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to write texture " + id, e);
                }

                try {
                    byte[] bytes = imageToByteArray(image);
                    cachedOutput.writeIfNeeded(path, bytes, Hashing.sha1().hashBytes(bytes));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to write texture to cache " + id, e);
                }
            };

            return this.registries.thenCompose(provider -> CompletableFuture.runAsync(() -> generate(
                    resourceManager, provider, output
            )));
        }
    }

    private static byte[] imageToByteArray(NativeImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] array;
        try {
            WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);

            try {
                //noinspection DataFlowIssue
                if (!NativeImageInvoker.class.cast(image).pneumonocore$writeToChannel(writableByteChannel)) {
                    throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
                }

                array = byteArrayOutputStream.toByteArray();
            } catch (Throwable var7) {
                if (writableByteChannel != null) {
                    try {
                        writableByteChannel.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (writableByteChannel != null) {
                writableByteChannel.close();
            }
        } catch (Throwable e) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable var5) {
                e.addSuppressed(var5);
            }

            throw e;
        }

        byteArrayOutputStream.close();
        return array;
    }

    private static /*? if >=1.20.5 {*/PackLocationInfo/*?} else {*//*String*//*?}*/ makePackInfo(String name) {
        //? if >=1.20.5 {
        return new PackLocationInfo(name, Component.literal(name), PackSource.BUILT_IN, Optional.empty());
        //?} else {
        /*return name;
        *///?}
    }

    public abstract void generate(ResourceManager manager, HolderLookup.Provider registries, BiConsumer<NativeImage, Identifier> output);

    public void applyAlpha(ResourceManager manager, BiConsumer<NativeImage, Identifier> output, Identifier baseId, Identifier alphaSourceId, Identifier outputId) {
        try (
                NativeImage base = readImage(manager, baseId);
                NativeImage alphaSource = readImage(manager, alphaSourceId)
        ) {
            int width = base.getWidth();
            int height = base.getHeight();
            if (width != alphaSource.getWidth() || height != alphaSource.getHeight()) {
                throw new IllegalStateException("Texture sources have incompatible dimensions");
            }

            NativeImage newImage = new NativeImage(width, height, true);

            for (int x = 0; x < width; x++) { for (int y = 0; y < height; y++) {
                //? if >=1.21.2 {
                int baseColor = base.getPixel(x, y);
                int alphaSourceColor = alphaSource.getPixel(x, y);
                int newColor = (baseColor & 0x00FFFFFF) + (alphaSourceColor & 0xFF000000);
                newImage.setPixel(x, y, newColor);
                //?} else {
                /*// For some fucking reason, getPixelRGBA returns a color in ARGB
                int baseColor = base.getPixelRGBA(x, y);
                int alphaSourceColor = alphaSource.getPixelRGBA(x, y);
                int newColor = (baseColor & 0x00FFFFFF) + (alphaSourceColor & 0xFF000000);
                newImage.setPixelRGBA(x, y, newColor);
                *///?}
            }}

            output.accept(newImage, outputId);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to generate texture using " + baseId + " and " + alphaSourceId, e
            );
        }
    }

    public void applyColorAndAlpha(ResourceManager manager, BiConsumer<NativeImage, Identifier> output, Identifier baseId, Identifier colorSourceId, Identifier alphaSourceId, Identifier outputId) {
        try (
                NativeImage base = readImage(manager, baseId);
                NativeImage colorSource = readImage(manager, colorSourceId);
                NativeImage alphaSource = readImage(manager, alphaSourceId)
        ) {
            int width = base.getWidth();
            int height = base.getHeight();
            if (
                    width != colorSource.getWidth() || height != colorSource.getHeight() ||
                    width != alphaSource.getWidth() || height != alphaSource.getHeight()
            ) {
                throw new IllegalStateException("Texture sources have incompatible dimensions");
            }

            NativeImage newImage = new NativeImage(width, height, true);

            for (int x = 0; x < width; x++) { for (int y = 0; y < height; y++) {
                //? if >=1.21.2 {
                int baseColor = base.getPixel(x, y);
                int colorSourceColor = colorSource.getPixel(x, y);
                int alphaSourceColor = alphaSource.getPixel(x, y);
                int newColorRGB = multiplyRGB(baseColor & 0x00FFFFFF, colorSourceColor & 0x00FFFFFF);
                int newColor = newColorRGB + (alphaSourceColor & 0xFF000000);
                newImage.setPixel(x, y, newColor);
                //?} else {
                /*// For some fucking reason, getPixelRGBA returns a color in ARGB
                int baseColor = base.getPixelRGBA(x, y);
                int colorSourceColor = colorSource.getPixelRGBA(x, y);
                int alphaSourceColor = alphaSource.getPixelRGBA(x, y);
                int newColorRGB = multiplyRGB(baseColor & 0x00FFFFFF, colorSourceColor & 0x00FFFFFF);
                int newColor = newColorRGB + (alphaSourceColor & 0xFF000000);
                newImage.setPixelRGBA(x, y, newColor);
                *///?}
            }}

            output.accept(newImage, outputId);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to generate texture using " + baseId + ", " + colorSourceId + ", and " + alphaSourceId, e
            );
        }
    }

    public static int multiplyRGB(int color1, int color2) {
        int r = (((color1 & 0xFF0000) >> 16) * ((color2 & 0xFF0000) >> 16)) / 255;
        int g = (((color1 & 0x00FF00) >> 8) * ((color2 & 0x00FF00) >> 8)) / 255;
        int b = ((color1 & 0x0000FF) * (color2 & 0x0000FF)) / 255;
        return (r << 16) + (g << 8) + b;
    }

    public NativeImage readImage(ResourceManager manager, Identifier id) throws IOException {
        Optional<Resource> resource = manager.getResource(id);
        if (resource.isPresent()) {
            try (InputStream stream = resource.get().open()) {
                return NativeImage.read(stream);
            }
        } else {
            throw new IOException("Couldn't find texture " + id);
        }
    }

    public Identifier texture(String namespace, String path) {
        return Identifier./*? if >=1.21 {*/fromNamespaceAndPath/*?} else {*//*tryBuild*//*?}*/(
                namespace, "textures/" + path + ".png"
        );
    }

    public Identifier template(String namespace, String path) {
        return Identifier./*? if >=1.21 {*/fromNamespaceAndPath/*?} else {*//*tryBuild*//*?}*/(
                namespace, "texture_templates/" + path + ".png"
        );
    }

    @Override
    public String getName() {
        return "Textures";
    }
}
