package net.pneumono.pneumonocore.config_api;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.enums.ConfigEnv;

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
     * Creates a new configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
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

    public String valueToJson(boolean useDefaultValues) {
        return useDefaultValues || loadedValue == null ? defaultValue.toString() : loadedValue.toString();
    }

    public abstract AbstractConfiguration<T> fromElement(NbtElement element);
    public abstract AbstractConfiguration<T> fromElement(JsonElement element);

    public boolean isClientSide() {
        return environment == ConfigEnv.CLIENT;
    }

    public ConfigEnv getEnvironment() {
        return environment;
    }

    public String getModID() {return modID;}

    public String getName() {
        return name;
    }

    public Identifier getID() {
        return Identifier.of(modID, name);
    }

    public String getTranslationKey() {
        return "configs." + modID + "." + name;
    }

    public String getTranslationKey(String suffix) {
        return getTranslationKey() + "." + suffix;
    }

    public String getTooltipTranslationKey() {
        return getTranslationKey("tooltip");
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
     * Will return its default value if the configuration has not been properly registered with {@link ConfigApi#register(String, AbstractConfiguration[])}.
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

        ModConfigurations modConfigs = ConfigApi.getModConfigs(this.modID);
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

    public T getReloadableLoadedValue(boolean looped) {
        return loadedValue != null || looped ? loadedValue : getReloadedValue();
    }

    private T getReloadedValue() {
        ConfigApi.reload(modID);
        return getValue(true);
    }

    @SuppressWarnings("unchecked")
    protected void setLoadedValue(Object value) {
        if (value != null) {
            loadedValue = (T) value;
        }
    }

    @SuppressWarnings("unchecked")
    public void setImportedValue(AbstractConfiguration<?> config) {
        importedValue = (T) config.loadedValue;
    }

    public abstract String getClassID();
}