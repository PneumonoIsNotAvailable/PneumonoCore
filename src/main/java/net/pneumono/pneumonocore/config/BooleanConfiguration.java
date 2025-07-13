package net.pneumono.pneumonocore.config;

@Deprecated
public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue) {
        super(modID, name, environment, defaultValue);
    }
}
