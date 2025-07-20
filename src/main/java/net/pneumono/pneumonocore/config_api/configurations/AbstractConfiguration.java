package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

/**
 * Saved Value - The value loaded from config files.
 * Effective Value - The value that is actually used. Can differ from Saved Value due to the Load Type system or server-client config sync
 */
public abstract class AbstractConfiguration<T> {
    private boolean registered = false;
    private Identifier id;
    private final ConfigSettings settings;
    private final T defaultValue;
    private final ConfigValue<T> savedValue;
    private final ConfigValue<T> effectiveValue;

    /**
     * Creates a new configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param defaultValue The default value of the configuration.
     */
    public AbstractConfiguration(T defaultValue, ConfigSettings settings) {
        this.settings = settings;
        this.defaultValue = defaultValue;
        this.savedValue = new ConfigValue<>(this, defaultValue);
        this.effectiveValue = new ConfigValue<>(this, defaultValue);
    }

    public abstract Codec<T> getValueCodec();
    protected abstract Identifier getConfigTypeId();

    public T getValue() {
        if (!this.registered) throw new IllegalStateException("Cannot get value of unregistered configuration.");

        boolean enabled = true;
        if (this.settings.condition != null) {
            enabled = this.settings.condition.get();
        }
        if (enabled && this.settings.parent != null) {
            enabled = this.settings.parent.test(this.effectiveValue.get());
        }

        return enabled ? this.effectiveValue.get() : this.defaultValue;
    }

    public Info info() {
        return new Info();
    }

    protected void register(Identifier id) {
        this.id = id;
        this.registered = true;
    }

    public class Info {
        public T getDefaultValue() {
            return defaultValue;
        }

        public Identifier getId() {
            if (!registered) throw new IllegalStateException("Cannot get id of unregistered configuration.");
            return id;
        }

        public String getModID() {
            return this.getId().getNamespace();
        }

        public String getName() {
            return this.getId().getPath();
        }

        public ConfigSettings getSettings() {
            return settings.copy();
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
                return this.getSettings().parent.configuration().get();
            } else {
                return null;
            }
        }

        public boolean isEnabled(Object object) {
            if (this.getSettings().parent != null) {
                return this.getSettings().parent.test(object);
            }
            return true;
        }

        public Identifier getConfigTypeId() {
            return AbstractConfiguration.this.getConfigTypeId();
        }
    }

    protected ConfigValue<T> getSavedValue() {
        return this.savedValue;
    }

    protected ConfigValue<T> getEffectiveValue() {
        return this.effectiveValue;
    }

    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }
}