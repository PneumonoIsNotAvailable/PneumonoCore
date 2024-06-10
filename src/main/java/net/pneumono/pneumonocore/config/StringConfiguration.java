package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

@SuppressWarnings("unused")
public class StringConfiguration extends AbstractConfiguration<String> {
    /**
     * Creates a new string configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    private StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    public StringConfiguration fromElement(JsonElement element) {
        return new StringConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public String valueFromElement(JsonElement element) {
        return element.getAsString();
    }

    @Override
    public String getClassID() {
        return "StringConfiguration";
    }
}
