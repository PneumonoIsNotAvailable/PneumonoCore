package net.pneumono.pneumonocore.config;

@Deprecated
public class StringConfiguration extends AbstractConfiguration<String> {
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(modID, name, environment, defaultValue);
    }
}
