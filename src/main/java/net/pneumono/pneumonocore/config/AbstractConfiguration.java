package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

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
     * Creates a new configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
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
        this.loadedValue = defaultValue;
    }

    protected AbstractConfiguration(String modID, String name, ConfigEnv environment, T defaultValue, T loadedValue) {
        this.modID = modID;
        this.name = name;
        this.environment = environment;
        this.defaultValue = defaultValue;
        this.loadedValue = loadedValue;
    }

    protected String valueToJson(boolean useDefaultValues) {
        return useDefaultValues || loadedValue == null ? defaultValue.toString() : loadedValue.toString();
    }

    protected abstract AbstractConfiguration<T> fromElement(JsonElement element);

    public abstract T valueFromElement(JsonElement element);

    public boolean isClientSide() {
        return environment == ConfigEnv.CLIENT;
    }

    public String getModID() {return modID;}

    public String getName() {
        return name;
    }

    public Identifier getID() {
        return new Identifier(modID, name);
    }

    public String getTranslationKey() {
        return "configs." + modID + "." + name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    @SuppressWarnings("unused")
    protected T getLoadedValue() {
        return loadedValue;
    }

    /**
     * Returns the configuration value (from the client or server, depending on whether the configuration is client or server-side).<p>
     *
     * Will return the client-side value if it does not have the server-side value.<p>
     * Will return its default value if the configuration has not been properly registered with {@link Configs#register(AbstractConfiguration)}.
     *
     * @return The configuration value.
     */
    public T getValue() {
        return getValue(false);
    }

    @SuppressWarnings("unchecked")
    private T getValue(boolean looped) {
        // GENEVA VIOLATION
        AbstractConfiguration<?> thisConfig = null;

        ModConfigurations modConfigs = Configs.CONFIGS.get(modID);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (Objects.equals(config.name, name)) {
                    thisConfig = config;
                    break;
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

    @SuppressWarnings("unchecked")
    protected void setLoadedValue(Object value) {
        if (value != null) {
            loadedValue = (T) value;
        }
    }

    @SuppressWarnings("unchecked")
    protected void setImportedValue(AbstractConfiguration<?> config) {
        importedValue = (T) config.loadedValue;
    }

    public abstract String getClassID();
}