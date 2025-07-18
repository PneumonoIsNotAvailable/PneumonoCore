package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    private final int minValue;
    private final int maxValue;

    /**
     * Creates a new integer configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Integer configurations have set minimum and maximum values. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param minValue The minimum value the configuration can be set to.
     * @param maxValue The maximum value the configuration can be set to.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public IntegerConfiguration(String modID, String name, Integer defaultValue, int minValue, int maxValue, ConfigSettings settings) {
        super(modID, name, validateDefaultValue(defaultValue, minValue, maxValue), settings);
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
        return Codec.INT;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("integer");
    }
}
