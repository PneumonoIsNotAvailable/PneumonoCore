package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

@SuppressWarnings("unused")
public class StringConfiguration extends AbstractConfiguration<String> {
    /**
     * Creates a new string configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * String configurations are not yet supported by the built-in configuration screen, because I'm dumb and don't know how to make that work.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     * @param tooltip The translation key of the tooltip to show on the config screen. In the case of string configurations, this is never used.
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String tooltip) {
        super(modID, name, environment, defaultValue, tooltip);
    }

    protected StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String loadedValue, String tooltip) {
        super(modID, name, environment, defaultValue, loadedValue, tooltip);
    }

    @Override
    protected StringConfiguration fromElement(JsonElement element) {
        return new StringConfiguration(modID, name, environment, getDefaultValue(), element.getAsString(), tooltip);
    }
}
