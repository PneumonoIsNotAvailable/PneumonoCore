package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class BoundedFloatConfiguration extends AbstractConfiguration<Float> {
    /**
     * Creates a new configuration that stores a {@code float} between 0 and 1.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    @SuppressWarnings("unused")
    public BoundedFloatConfiguration(float defaultValue, ConfigSettings settings) {
        super(validateDefaultValue(defaultValue), settings);
    }

    private static float validateDefaultValue(float defaultValue) {
        if (defaultValue > 1 || defaultValue < 0) {
            throw new IllegalArgumentException("Float Configurations cannot have a default value outside 0-1!");
        }
        return defaultValue;
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.either(
                Codec.FLOAT, Codec.STRING.comapFlatMap(this::parseString, Object::toString)
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
    private DataResult<Float> parseString(String string) {
        try {
            return DataResult.success(Float.parseFloat(string));
        } catch (NumberFormatException e) {
            return DataResult.error(() -> "'" + string + "' cannot be parsed. " + e.getMessage());
        }
    }

    @Override
    protected ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("bounded_float");
    }
}
