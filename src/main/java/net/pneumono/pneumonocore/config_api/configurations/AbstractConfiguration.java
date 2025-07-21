package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a configurable value.
 *
 * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
 * {@link ConfigApi#finishRegistry} must be called.
 *
 * <p>Configurations use the translation key {@code configs.<modId>.<configName>} for their name,
 * and {@code configs.<modId>.<configName>.tooltip} for a short description of that they affect.
 *
 * <p>A configuration is "enabled" if it passes its condition, and if its parent has the correct value.
 * "Disabled" configurations will use their default value and will not appear in config screens.
 *
 * <p>Configuration enabling is intended to be used when a configuration is only relevant under certain conditions.
 * For example, a configuration affecting the health of an entity is irrelevant if that entity itself has been disabled.
 * There are two main intended ways to handle configuration enabling,
 * although {@link ConfigSettings#condition(Supplier) ConfigSettings.condition} will accept any supplier.
 * <ul>
 *     <li>For when a configuration is dependent on the value of another configuration
 *     (e.g. the previously stated entity example).
 *     This should be handled via {@link ConfigSettings#parent(AbstractConfiguration, Predicate) ConfigSettings.parent}.
 *     <li>For when a configuration is dependent on another mod being installed
 *     (e.g. configurations that effect features added for compatibility with other mods).
 *     This should be handled via {@link ConfigSettings#requiresMod}.
 * </ul>
 *
 * <p>Stores 3 values of its type:
 * <ul>
 *     <li>{@code defaultValue}, which represents the default value of the configuration,
 *     And is used when no value has been set or in the case of errors.
 *     <li>{@code savedValue}, which represents the value saved to the config file.
 *     <li>{@code effectiveValue}, which represents the value actually used and returned by {@code getValue}.
 *     <p>This may differ from {@code savedValue} if the value hasn't been updated yet (see {@link LoadType}),
 *     or if the client has received a config sync packet from the server.
 * </ul>
 *
 * <p>{@link #info()} was created to reduce clutter when using this class,
 * as the vast majority of mods will never need to use any methods
 * other than {@link #getValue()} and {@link #getConfigTypeId()}
 *
 * <p>Throws exceptions when {@code getValue} is used before the config has been properly registered.
 */
public abstract class AbstractConfiguration<T> {
    private boolean registered = false;
    private Identifier id;
    private final ConfigSettings settings;
    private final T defaultValue;
    private final ConfigValue<T> savedValue;
    private final ConfigValue<T> effectiveValue;

    /**
     * Creates a new configuration.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    public AbstractConfiguration(T defaultValue, ConfigSettings settings) {
        this.settings = settings;
        this.defaultValue = defaultValue;
        this.savedValue = new ConfigValue<>(this, defaultValue);
        this.effectiveValue = new ConfigValue<>(this, defaultValue);
    }

    /**
     * @return A codec for this configuration's value.
     */
    public abstract Codec<T> getValueCodec();

    /**
     * @return This configuration's type ID, used client-side for the config screen.
     */
    protected abstract Identifier getConfigTypeId();

    /**
     * Returns the value of this configuration.
     *
     * <p>More specifically, returns this configuration's {@linkplain #effectiveValue effective value} while enabled,
     * and it's default value otherwise.
     */
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

    /**
     * @return An object that provides access to more values from this configuration.
     */
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

        public String getModId() {
            return this.getId().getNamespace();
        }

        public String getName() {
            return this.getId().getPath();
        }

        /**
         * Returns a copy of this configuration's settings.
         *
         * <p>Editing the copy will not edit the original settings of the configuration.
         */
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

        /**
         * A configuration is "enabled" if it passes its condition, and if its parent has the correct value.
         *
         * <p>"Disabled" configurations will use their default value.
         */
        public boolean isEnabled(Object object) {
            boolean enabled = true;
            if (this.getSettings().condition != null) {
                enabled = this.getSettings().condition.get();
            }
            if (enabled && this.getSettings().parent != null) {
                enabled = this.getSettings().parent.test(object);
            }
            return enabled;
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

    /**
     * Used only for backwards compatibility.
     */
    @Deprecated
    protected void setCategory(String category) {
        this.settings.category(category);
    }
}