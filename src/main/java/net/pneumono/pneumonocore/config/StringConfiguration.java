package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.StringConfiguration StringConfiguration} instead.
 */
@Deprecated
public class StringConfiguration extends AbstractConfiguration<String, net.pneumono.pneumonocore.config_api.configurations.StringConfiguration> {
    public StringConfiguration(String modId, String name, ConfigEnv environment, String defaultValue) {
        super(modId, name, new net.pneumono.pneumonocore.config_api.configurations.StringConfiguration(defaultValue, environment.toSettings()));
    }
}
