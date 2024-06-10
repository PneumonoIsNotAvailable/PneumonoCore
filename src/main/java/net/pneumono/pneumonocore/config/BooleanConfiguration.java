package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    /**
     * Creates a new boolean configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    private BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue, boolean loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    public BooleanConfiguration fromElement(JsonElement element) {
        return new BooleanConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public Boolean valueFromElement(JsonElement element) {
        return element.getAsBoolean();
    }

    @Override
    public String getClassID() {
        return "BooleanConfiguration";
    }
}
