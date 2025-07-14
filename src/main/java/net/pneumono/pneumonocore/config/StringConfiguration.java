package net.pneumono.pneumonocore.config;

@Deprecated
public class StringConfiguration extends AbstractConfiguration<String> {
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(name, new net.pneumono.pneumonocore.config.configuration.StringConfiguration(defaultValue, createSettings(environment)));
    }
}
