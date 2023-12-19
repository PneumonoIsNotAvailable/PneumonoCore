package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    /**
     * Creates a new boolean configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "<modID>.configs.<name>"} in config menus.<p>
     * Boolean configuration values use the translation keys {@code "<modID>.configs.<name>.enabled"} and {@code "<modID>.configs.<name>.disabled"}.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     * @param tooltip The translation key of the tooltip to show on the config screen. Can be null, in which case no tooltip will be displayed.
     */
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue, String tooltip) {
        super(modID, name, environment, defaultValue, tooltip);
    }

    private BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue, boolean loadedValue, String tooltip) {
        super(modID, name, environment, defaultValue, loadedValue, tooltip);
    }

    @Override
    protected BooleanConfiguration fromElement(JsonElement element) {
        return new BooleanConfiguration(modID, name, environment, getDefaultValue(), element.getAsBoolean(), tooltip);
    }
}
