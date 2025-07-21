package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration TimeConfiguration} instead.
 */
@Deprecated
public class TimeConfiguration extends AbstractConfiguration<Long, net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration> {
    public TimeConfiguration(String modId, String name, ConfigEnv environment, Long defaultValue) {
        super(modId, name, new net.pneumono.pneumonocore.config_api.configurations.TimeConfiguration(defaultValue, environment.toSettings()));
    }
}
