package net.pneumono.pneumonocore.config_api.configurations;

import net.fabricmc.loader.api.FabricLoader;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Stores a number of settings for a given configuration.
 *
 * <p>Details on what each setting does are specified in the methods that change them.
 */
@SuppressWarnings("unused")
public class ConfigSettings {
    protected boolean clientSided = false;
    protected String category = "misc";
    protected LoadType loadType = LoadType.RELOAD;
    @Nullable
    protected Supplier<Boolean> condition = null;
    @Nullable
    protected Parent<?> parent = null;

    public ConfigSettings copy() {
        return new ConfigSettings()
                .clientSide(clientSided)
                .category(category)
                .loadType(loadType)
                .condition(condition)
                .parent(parent);
    }

    /**
     * Sets whether this configuration is client-side.
     *
     * <p>Client-side configurations ignore synced values from the server,
     * and can be updated using client-side config screens.
     *
     * <p>Configurations that affect rendering or are purely cosmetic should be client-side.
     */
    public ConfigSettings clientSide(boolean value) {
        this.clientSided = value;
        return this;
    }

    /**
     * @see #clientSide(boolean)
     */
    public ConfigSettings clientSide() {
        return this.clientSide(true);
    }

    /**
     * Sets this configuration's category.
     *
     * <p>Config categories affect how the configurations are ordered in the config screen.
     * Categories use the translation key {@code configs.category.<modId>.<categoryName>}.
     *
     * <p>Configs with no specified category are automatically put in the "Miscellaneous" category,
     * which uses a translation key already specified by PneumonoCore.
     */
    public ConfigSettings category(String category) {
        if (category == null) throw new IllegalArgumentException("Category cannot be null.");
        this.category = category;
        return this;
    }

    /**
     * Sets this configuration's load type.
     *
     * <p>Load types dictate when the configuration's effective value should be updated.
     * For example, if a configuration handles whether something is registered,
     * it should not be updated until the game restarts (since registries cannot be changed) until then.
     *
     * <p>There are 3 load types:
     * <ul>
     *     <li>{@link LoadType#INSTANT INSTANT}, which allows the value to be updated as soon as it is changed.
     *     <li>{@link LoadType#RELOAD RELOAD}, which allows the value to be updated when resources are reloaded.
     *     <li>{@link LoadType#RESTART RESTART}, which only allows the value to updated when the game is restarted.
     * </ul>
     * <p>{@code RELOAD} is used by default.
     */
    public ConfigSettings loadType(LoadType loadType) {
        if (loadType == null) throw new IllegalArgumentException("Load Type cannot be null.");
        this.loadType = loadType;
        return this;
    }

    /**
     * Sets this configuration's condition.
     *
     * <p>Conditions are one of the factors affecting whether a configuration is "enabled".
     * For details on configuration enabling, see {@link AbstractConfiguration}
     */
    public ConfigSettings condition(Supplier<Boolean> condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Sets this configuration's condition to require the mod with that mod ID to be loaded.
     */
    public ConfigSettings requiresMod(String modId) {
        return condition(() -> FabricLoader.getInstance().isModLoaded(modId));
    }

    /**
     * Sets this configuration's parent.
     *
     * <p>Configuration Parents are practically just more complicated {@linkplain #condition(Supplier) conditions}.
     * They should be used when a configuration depends on the value of another,
     * and use predicates rather than suppliers so that config screens can work properly.
     */
    public ConfigSettings parent(Parent<?> parent) {
        this.parent = parent;
        return this;
    }

    /**
     * @see #parent(Parent)
     */
    public <T> ConfigSettings parent(AbstractConfiguration<T> configuration, Predicate<T> predicate) {
        return parent(new Parent<>(configuration, predicate));
    }

    public record Parent<T>(Supplier<AbstractConfiguration<T>> configuration, Predicate<T> enabledPredicate) {
        public Parent(AbstractConfiguration<T> configuration, Predicate<T> enabledPredicate) {
            this(() -> configuration, enabledPredicate);
        }

        public Parent(AbstractConfiguration<T> configuration) {
            this(() -> configuration, object -> true);
        }

        public Supplier<Boolean> createCondition() {
            return () -> enabledPredicate.test(configuration.get().getValue());
        }

        public boolean test() {
            return createCondition().get();
        }

        @SuppressWarnings("unchecked")
        public boolean test(Object parentValue) {
            try {
                return enabledPredicate.test((T) parentValue);
            } catch (ClassCastException e) {
                return false;
            }
        }
    }
}
