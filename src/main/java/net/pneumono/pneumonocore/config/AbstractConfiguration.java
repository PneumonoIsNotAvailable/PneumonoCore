package net.pneumono.pneumonocore.config;

import net.minecraft.util.Identifier;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration} instead.
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
        return this.configuration.getInfo().isClientSided();
    }

    public String getModID() {
        return this.modId;
    }

    public String getName() {
        return this.name;
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
        return this.configuration.getInfo().getDefaultValue();
    }

    public T getValue() {
        return this.configuration.getValue();
    }
}