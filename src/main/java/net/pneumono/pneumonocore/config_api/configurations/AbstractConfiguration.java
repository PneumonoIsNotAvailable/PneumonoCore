package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public abstract class AbstractConfiguration<T> {
    private boolean registered = false;
    private final String modID;
    private final String name;
    private final ConfigSettings settings;
    private final T defaultValue;
    private T loadedValue;
    private T syncedValue;

    /**
     * Creates a new configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param defaultValue The default value of the configuration.
     */
    public AbstractConfiguration(String modID, String name, T defaultValue, ConfigSettings settings) {
        this.modID = modID;
        this.name = name;
        this.settings = settings;
        this.defaultValue = defaultValue;
        this.loadedValue = defaultValue;
        this.syncedValue = defaultValue;
    }

    public abstract Codec<T> getValueCodec();
    public abstract Identifier getConfigTypeId();

    public T getValue() {
        if (!this.registered) throw new IllegalStateException("Cannot get value of unregistered configuration.");
        return this.syncedValue;
    }

    protected void setRegistered() {
        this.registered = true;
    }

    protected void setLoadedValue(T value) {
        this.loadedValue = value;
    }

    protected void setSyncedValue(T value) {
        this.syncedValue = value;
    }

    protected T getLoadedValue() {
        return loadedValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean isClientSided() {
        return this.settings.clientSided;
    }

    public String getCategory() {
        return this.settings.category;
    }

    public String getModID() {
        return modID;
    }

    public String getName() {
        return name;
    }

    public Identifier getID() {
        return Identifier.of(modID, name);
    }

    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }
}