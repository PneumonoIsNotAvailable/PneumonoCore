package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class FloatConfiguration extends AbstractConfiguration<Float> {
    /**
     * Creates a new float configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Float configurations have a minimum value of 0 and a maximum value of 1. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value (0 or 1)
     *
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public FloatConfiguration(float defaultValue, ConfigSettings settings) {
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
    protected Identifier getConfigTypeId() {
        return PneumonoCore.identifier("float");
    }
}
