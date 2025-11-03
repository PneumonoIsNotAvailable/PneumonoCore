package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class TimeConfiguration extends AbstractConfiguration<Long> {
    /**
     * Creates a new configuration that stores a {@code long} representing an amount of time.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    @SuppressWarnings("unused")
    public TimeConfiguration(long defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Long> getValueCodec() {
        return Codec.either(
                Codec.LONG, Codec.STRING.comapFlatMap(this::parseString, Object::toString)
        ).xmap(
                either -> {
                    if (either.left().isPresent()) {
                        return either.left().get();
                    } else if (either.right().isPresent()) {
                        return either.right().get();
                    }
                    return null;
                },
                Either::left
        );
    }

    // Exists for backwards compatibility - previous config system saved everything as strings
    private DataResult<Long> parseString(String string) {
        try {
            return DataResult.success(Long.parseLong(string));
        } catch (NumberFormatException e) {
            return DataResult.error(() -> "'" + string + "' cannot be parsed. " + e.getMessage());
        }
    }

    @Override
    protected ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("time");
    }
}
