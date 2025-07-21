package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration BooleanConfiguration} instead.
 */
@Deprecated
public class BooleanConfiguration extends AbstractConfiguration<Boolean, net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration> {
    public BooleanConfiguration(String modId, String name, ConfigEnv environment, boolean defaultValue) {
        super(modId, name, new net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration(defaultValue, environment.toSettings()));
    }
}
