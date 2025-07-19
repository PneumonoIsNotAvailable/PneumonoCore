package net.pneumono.pneumonocore.config_api.configurations;

import net.fabricmc.loader.api.FabricLoader;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ConfigSettings {
    protected boolean clientSided = false;
    protected String category = "misc";
    protected LoadType loadType = LoadType.RELOAD;
    @Nullable
    protected Supplier<Boolean> condition = null;
    @Nullable
    protected Supplier<AbstractConfiguration<?>> parent = null;

    public ConfigSettings copy() {
        return new ConfigSettings()
                .clientSide(clientSided)
                .category(category)
                .loadType(loadType)
                .condition(condition)
                .parent(parent);
    }

    public ConfigSettings clientSide(boolean value) {
        this.clientSided = value;
        return this;
    }

    public ConfigSettings clientSide() {
        return this.clientSide(true);
    }

    public ConfigSettings category(String category) {
        if (category == null) throw new IllegalArgumentException("Category cannot be null.");
        this.category = category;
        return this;
    }

    public ConfigSettings loadType(LoadType loadType) {
        if (loadType == null) throw new IllegalArgumentException("Load Type cannot be null.");
        this.loadType = loadType;
        return this;
    }

    public ConfigSettings condition(Supplier<Boolean> condition) {
        this.condition = condition;
        return this;
    }

    public ConfigSettings requiresMod(String modId) {
        return condition(() -> FabricLoader.getInstance().isModLoaded(modId));
    }

    public <T> ConfigSettings parent(AbstractConfiguration<T> configuration, Predicate<T> predicate) {
        parent(() -> configuration);
        return condition(() -> predicate.test(configuration.getValue()));
    }

    public ConfigSettings parent(Supplier<AbstractConfiguration<?>> parent) {
        this.parent = parent;
        return this;
    }
}
