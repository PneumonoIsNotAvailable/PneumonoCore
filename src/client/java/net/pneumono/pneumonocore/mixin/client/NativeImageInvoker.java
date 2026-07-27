package net.pneumono.pneumonocore.mixin.client;

import com.mojang.blaze3d.platform.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.channels.WritableByteChannel;

@Mixin(NativeImage.class)
public interface NativeImageInvoker {
    @Unique
    @Invoker("writeToChannel")
    boolean writeToChannel(WritableByteChannel output);
}
