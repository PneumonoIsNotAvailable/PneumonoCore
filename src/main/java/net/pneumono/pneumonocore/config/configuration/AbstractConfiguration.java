package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.LoadType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class AbstractConfiguration<T> {
    private Identifier id;
    private final Settings settings;
    private final Predicate<T> validator;
    /**
     * The default value.
     */
    private final T defaultValue;
    /**
     * The value that is saved in the config file.
     */
    private T savedValue;
    /**
     * The value that is currently loaded.
     *
     * <p>Differs from {@link AbstractConfiguration#savedValue} in that the saved value
     */
    private T loadedValue;

    public AbstractConfiguration(T defaultValue, Settings settings) {
        this(defaultValue, value -> true, settings);
    }

    public AbstractConfiguration(T defaultValue, Predicate<T> validator, Settings settings) {
        if (!validator.test(defaultValue)) throw new IllegalArgumentException("Default config value must be able to pass validation check");
        this.defaultValue = defaultValue;
        this.validator = validator;
        this.settings = settings;
    }

    public abstract Codec<T> getValueCodec();

    public T get() {
        if (this.id == null) throw new IllegalStateException("Cannot get config value of an unregistered config!");
        return this.loadedValue;
    }

    public Identifier getId() {
        return id;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getSavedValue() {
        return savedValue;
    }

    public void setSavedValue(T savedValue) {
        this.savedValue = savedValue;
    }

    public void setLoadedValue(T loadedValue) {
        this.loadedValue = loadedValue;
    }

    public boolean canAccept(T value) {
        return this.validator.test(value);
    }

    public void setId(Identifier id) {
        if (this.id != null) throw new IllegalStateException("Cannot give an ID to a config that has already been registered!");
        this.id = id;
    }

    @Deprecated
    public void setGroup(String group) {
        this.settings.group = group;
    }

    public static class Settings {
        @Nullable
        private String group = null;
        private LoadType loadType = LoadType.RESTART_EVERYTHING;
        private boolean clientSided = false;

        public Settings group(String group) {
            this.group = group;
            return this;
        }

        public Settings loadType(LoadType type) {
            this.loadType = type;
            return this;
        }

        public Settings clientSide(boolean value) {
            this.clientSided = value;
            return this;
        }

        public Settings clientSide() {
            return clientSide(true);
        }
    }
}
