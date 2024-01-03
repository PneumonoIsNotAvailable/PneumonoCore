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
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    // the differentiator only exists temporarily to make sure the deprecated constructor isn't called instead
    protected StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String loadedValue, boolean differentiator) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    public StringConfiguration fromElement(JsonElement element) {
        return new StringConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element), true);
    }

    @Override
    public String valueFromElement(JsonElement element) {
        return element.getAsString();
    }

    /**
     * Creates a new string configuration. Replaced by version that doesn't specify a tooltip translation key, since the same format is always used and there isn't any point.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     * @param tooltip Does nothing. Exists for backwards compatibility.
     */
    @SuppressWarnings("unused")
    @Deprecated
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String tooltip) {
        super(modID, name, environment, defaultValue);
    }
}
