package net.pneumono.pneumonocore.config_api.configurations;

public class ConfigManager {
    public static <T, C extends AbstractConfiguration<T>> void setValue(C configuration, T value) {
        setLoadedValue(configuration, value);
        setSyncedValue(configuration, value);
    }

    public static <T, C extends AbstractConfiguration<T>> void setLoadedValue(C configuration, T value) {
        configuration.setLoadedValue(value);
    }

    public static <T, C extends AbstractConfiguration<T>> void setSyncedValue(C configuration, T value) {
        configuration.setSyncedValue(value);
    }

    public static void setRegistered(AbstractConfiguration<?> configuration) {
        configuration.setRegistered();
    }

    public static <T, C extends AbstractConfiguration<T>> T getLoadedValue(C configuration) {
        return configuration.getLoadedValue();
    }
}
