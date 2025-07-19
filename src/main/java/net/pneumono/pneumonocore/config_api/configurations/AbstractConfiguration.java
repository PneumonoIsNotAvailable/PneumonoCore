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
    private T savedValue;
    private T effectiveValue;

    /**
     * Creates a new configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param defaultValue The default value of the configuration.
     */
    public AbstractConfiguration(T defaultValue, ConfigSettings settings) {
        this.settings = settings;
        this.defaultValue = defaultValue;
        this.savedValue = defaultValue;
        this.effectiveValue = defaultValue;
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
            enabled = this.settings.parent.test(this.effectiveValue);
        }

        return enabled ? this.effectiveValue : this.defaultValue;
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

    protected void setSavedValue(T value) {
        this.savedValue = value;
    }

    protected void setEffectiveValue(T value) {
        this.effectiveValue = value;
    }

    protected T getSavedValue() {
        return savedValue;
    }

    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }
}