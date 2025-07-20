package net.pneumono.pneumonocore.config_api.configurations;

public class ConfigValue<T> {
    private final AbstractConfiguration<T> configuration;
    private T value;

    public ConfigValue(AbstractConfiguration<T> configuration, T value) {
        this.configuration = configuration;
        this.value = value;
    }

    public AbstractConfiguration<T> getConfiguration() {
        return configuration;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
