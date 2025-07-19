package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

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
        return isEnabled() ? this.syncedValue : this.defaultValue;
    }

    public Info getInfo() {
        return new Info();
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

    public boolean isEnabled() {
        if (this.settings.condition != null) {
            return this.settings.condition.get();
        } else {
            return true;
        }
    }

    public Identifier getId() {
        if (!registered) throw new IllegalStateException("Cannot get id of unregistered configuration.");
        return this.id;
    }

    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }

    public class Info {
        public T getDefaultValue() {
            return AbstractConfiguration.this.defaultValue;
        }

        public Identifier getId() {
            return AbstractConfiguration.this.getId();
        }

        public String getModID() {
            return this.getId().getNamespace();
        }

        public String getName() {
            return this.getId().getPath();
        }

        public ConfigSettings getSettings() {
            return AbstractConfiguration.this.settings.copy();
        }

        public boolean isClientSided() {
            return this.getSettings().clientSided;
        }

        public String getCategory() {
            return this.getSettings().category;
        }

        public LoadType getLoadType() {
            return this.getSettings().loadType;
        }

        @Nullable
        public AbstractConfiguration<?> getParent() {
            if (this.getSettings().parent != null) {
                return this.getSettings().parent.get();
            } else {
                return null;
            }
        }
    }
}