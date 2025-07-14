package net.pneumono.pneumonocore.config;

@Deprecated
public abstract class AbstractConfiguration<T> {
    private final String name;
    private final net.pneumono.pneumonocore.config.configuration.AbstractConfiguration<T> config;

    public AbstractConfiguration(String name, net.pneumono.pneumonocore.config.configuration.AbstractConfiguration<T> config) {
        this.name = name;
        this.config = config;
    }

    protected static net.pneumono.pneumonocore.config.configuration.AbstractConfiguration.Settings createSettings(ConfigEnv env) {
        return new net.pneumono.pneumonocore.config.configuration.AbstractConfiguration.Settings()
                .clientSide(env == ConfigEnv.CLIENT);
    }

    public T getValue() {
        return this.config.get();
    }

    public String getName() {
        return name;
    }

    public net.pneumono.pneumonocore.config.configuration.AbstractConfiguration<T> getWrappedConfig() {
        return config;
    }
}