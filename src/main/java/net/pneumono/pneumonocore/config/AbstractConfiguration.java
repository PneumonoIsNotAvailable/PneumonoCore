package net.pneumono.pneumonocore.config;

import net.minecraft.util.Identifier;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class AbstractConfiguration<T, C extends net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<T>> {
    private final C configuration;

    public AbstractConfiguration(C configuration) {
        this.configuration = configuration;
    }

    public C getWrappedConfiguration() {
        return configuration;
    }

    public boolean isClientSide() {
        return this.configuration.isClientSided();
    }

    public String getModID() {
        return this.configuration.getModID();
    }

    public String getName() {
        return this.configuration.getName();
    }

    public Identifier getID() {
        return Identifier.of(getModID(), getName());
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
        return this.configuration.getDefaultValue();
    }

    public T getValue() {
        return this.configuration.getValue();
    }
}