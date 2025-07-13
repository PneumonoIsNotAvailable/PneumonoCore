package net.pneumono.pneumonocore.config;

@Deprecated
public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    public EnumConfiguration(String modID, String name, ConfigEnv environment, T defaultValue) {
        super(modID, name, environment, defaultValue);
    }
}
