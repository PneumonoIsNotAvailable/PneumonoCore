package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.util.math.MathHelper;

public class FloatConfiguration extends AbstractConfiguration<Float> {
    /**
     * Creates a new float configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Float configurations have a minimum value of 0 and a maximum value of 1. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value (0 or 1)
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public FloatConfiguration(String modID, String name, ConfigEnv environment, float defaultValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0F, 1F));
    }

    private FloatConfiguration(String modID, String name, ConfigEnv environment, float defaultValue, float loadedValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0F, 1F), MathHelper.clamp(loadedValue, 0F, 1F));
    }

    @Override
    public FloatConfiguration fromElement(JsonElement element) {
        return new FloatConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public Float valueFromElement(JsonElement element) {
        return element.getAsFloat();
    }

    @Override
    public String getClassID() {
        return "FloatConfiguration";
    }
}
