package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.util.math.MathHelper;

public class DoubleConfiguration extends AbstractConfiguration<Double> {
    /**
     * Creates a new double configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "<modID>.configs.<name>"} in config menus.<p>
     * Double configurations have a minimum value of 0 and a maximum value of 1. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value (0 or 1)
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public DoubleConfiguration(String modID, String name, ConfigEnv environment, double defaultValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0, 1));
    }

    private DoubleConfiguration(String modID, String name, ConfigEnv environment, double defaultValue, double loadedValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0, 1), MathHelper.clamp(loadedValue, 0, 1));
    }

    @Override
    public DoubleConfiguration fromElement(JsonElement element) {
        return new DoubleConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public Double valueFromElement(JsonElement element) {
        return element.getAsDouble();
    }

    /**
     * Creates a new double configuration. Replaced by version that doesn't specify a tooltip translation key, since the same format is always used and there isn't any point.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     * @param tooltip Does nothing. Exists for backwards compatibility.
     */
    @SuppressWarnings("unused")
    @Deprecated
    public DoubleConfiguration(String modID, String name, ConfigEnv environment, double defaultValue, String tooltip) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0, 1));
    }
}
