package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.util.math.MathHelper;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    private final int minValue;
    private final int maxValue;

    /**
     * Creates a new integer configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "<modID>.configs.<name>"} in config menus.
     * Double configurations have set minimum and maximum values. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param minValue The minimum value the configuration can be set to.
     * @param maxValue The maximum value the configuration can be set to.
     * @param defaultValue The default value of the configuration.
     * @param tooltip The translation key of the tooltip to show on the config screen. Can be null, in which case no tooltip will be displayed.
     */
    public IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue, String tooltip) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, minValue, maxValue), tooltip);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue, Integer loadedValue, String tooltip) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, minValue, maxValue), MathHelper.clamp(loadedValue, minValue, maxValue), tooltip);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    protected IntegerConfiguration fromElement(JsonElement element) {
        return new IntegerConfiguration(modID, name, environment, minValue, maxValue, getDefaultValue(), element.getAsInt(), tooltip);
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
