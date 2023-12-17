package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

import java.util.Objects;

public abstract class AbstractConfiguration<T> {
    protected String modID;
    protected final String name;
    protected final ConfigEnv environment;
    protected boolean registered = false;
    private final T defaultValue;
    private T loadedValue;
    private T importedValue;

    /**
     * Creates a new configuration. Register using {@link Configs#register(AbstractConfiguration)}.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    public AbstractConfiguration(String modID, String name, ConfigEnv environment, T defaultValue) {
        this.modID = modID;
        this.name = name;
        this.environment = environment;
        this.defaultValue = defaultValue;
    }

    protected AbstractConfiguration(String modID, String name, ConfigEnv environment, T defaultValue, T loadedValue) {
        this(modID, name, environment, defaultValue);
        this.loadedValue = loadedValue;
    }

    protected String valueToJson(boolean useDefaultValues) {
        return useDefaultValues || loadedValue == null ? defaultValue.toString() : loadedValue.toString();
    }

    protected abstract AbstractConfiguration<T> fromElement(JsonElement element);

    protected boolean isClientSide() {
        return environment == ConfigEnv.CLIENT;
    }

    protected String getModID() {return modID;}
    protected String getName() {
        return name;
    }
    protected T getDefaultValue() {
        return defaultValue;
    }
    protected T getLoadedValue() {
        return loadedValue;
    }

    /**
     * Returns the configuration value (from the client or server, depending on whether the configuration is client or server-side).<p>
     *
     * Will return the client-side value if it does not have the server-side value.<p>
     * Will return its default value if the configuration has not been properly registered with {@link Configs#register(AbstractConfiguration)}.<p>
     *
     * @return The configuration value.
     */
    public T getValue() {
        return getValue(false);
    }

    private T getValue(boolean looped) {
        // GENEVA VIOLATION
        AbstractConfiguration<?> thisConfig = null;

        search:
        for (ModConfigurations modConfigs : Configs.CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (Objects.equals(config.name, name)) {
                        thisConfig = config;
                        break search;
                    }
                }
            }
        }

        return thisConfig != null ? (thisConfig.isClientSide() ? (T) thisConfig.getReloadableLoadedValue(looped) : (thisConfig.importedValue != null ? (T) thisConfig.importedValue : (T) thisConfig.getReloadableLoadedValue(looped))) : null;
    }

    protected T getReloadableLoadedValue(boolean looped) {
        return loadedValue != null || looped ? loadedValue : getReloadedValue();
    }

    private T getReloadedValue() {
        Configs.reload(modID);
        return getValue(true);
    }

    protected void setLoadedValue(Object value) {
        if (value != null) {
            loadedValue = (T) value;
        }
    }

    protected void setImportedValue(AbstractConfiguration<?> config) {
        importedValue = (T) config.loadedValue;
    }
}