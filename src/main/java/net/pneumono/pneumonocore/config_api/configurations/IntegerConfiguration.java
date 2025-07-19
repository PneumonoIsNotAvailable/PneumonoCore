package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    private final int minValue;
    private final int maxValue;

    /**
     * Creates a new integer configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Integer configurations have set minimum and maximum values. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value.
     *
     * @param minValue The minimum value the configuration can be set to.
     * @param maxValue The maximum value the configuration can be set to.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public IntegerConfiguration(int defaultValue, int minValue, int maxValue, ConfigSettings settings) {
        super(validateDefaultValue(defaultValue, minValue, maxValue), settings);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static int validateDefaultValue(int defaultValue, int min, int max) {
        if (defaultValue > max || defaultValue < min) {
            throw new IllegalArgumentException("Integer Configurations cannot have a default value outside their bounds!");
        }
        return defaultValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.either(
                Codec.INT, Codec.STRING.comapFlatMap(this::parseString, Object::toString)
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
    private DataResult<Integer> parseString(String string) {
        try {
            return DataResult.success(Integer.parseInt(string));
        } catch (NumberFormatException e) {
            return DataResult.error(() -> "'" + string + "' cannot be parsed. " + e.getMessage());
        }
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("integer");
    }
}
