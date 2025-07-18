package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public abstract class AbstractConfiguration<T> {
    private boolean registered = false;
    private Identifier id;
    private final ConfigSettings settings;
    private final T defaultValue;
    private T loadedValue;
    private T syncedValue;

    /**
     * Creates a new configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param defaultValue The default value of the configuration.
     */
    public AbstractConfiguration(T defaultValue, ConfigSettings settings) {
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

    protected void setRegistered(Identifier id) {
        this.id = id;
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
        return this.getId().getNamespace();
    }

    public String getName() {
        return this.getId().getPath();
    }

    public Identifier getId() {
        if (!registered) throw new IllegalStateException("Cannot get id of unregistered configuration.");
        return this.id;
    }

    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }
}