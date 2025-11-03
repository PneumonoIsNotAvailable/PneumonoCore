package net.pneumono.pneumonocore.config;

import net.minecraft.resources.ResourceLocation;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration AbstractConfiguration} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class AbstractConfiguration<T, C extends net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<T>> {
    private final C configuration;
    private final String modId;
    private final String name;

    public AbstractConfiguration(String modId, String name, C configuration) {
        this.configuration = configuration;
        this.modId = modId;
        this.name = name;
    }

    public C getWrappedConfiguration() {
        return configuration;
    }

    public boolean isClientSide() {
        return this.configuration.info().isClientSided();
    }

    public String getModID() {
        return this.modId;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getID() {
        return ResourceLocation.tryBuild(getModID(), getName());
    }

    public String getTranslationKey() {
        return "configs." + getModID() + "." + getName();
    }

    public String getTranslationKey(String suffix) {
        return getTranslationKey() + "." + suffix;
    }

    public String getTooltipTranslationKey() {
        return getTranslationKey("tooltip");
    }

    public T getDefaultValue() {
        return this.configuration.info().getDefaultValue();
    }

    public T getValue() {
        return this.configuration.getValue();
    }
}