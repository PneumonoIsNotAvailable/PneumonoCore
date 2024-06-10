package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

public class TimeConfiguration extends AbstractConfiguration<Long> {
    /**
     * Creates a new time configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Time configurations are used for configuring amounts of time, e.g. the time taken for an item to be used.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public TimeConfiguration(String modID, String name, ConfigEnv environment, Long defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    protected TimeConfiguration(String modID, String name, ConfigEnv environment, Long defaultValue, Long loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    protected TimeConfiguration fromElement(JsonElement element) {
        return new TimeConfiguration(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public Long valueFromElement(JsonElement element) {
        return element.getAsLong();
    }

    @Override
    public String getClassID() {
        return "TimeConfiguration";
    }
}
