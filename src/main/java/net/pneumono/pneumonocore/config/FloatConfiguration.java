package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration FloatConfiguration} instead.
 */
@Deprecated
public class FloatConfiguration extends AbstractConfiguration<Float, net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration> {
    public FloatConfiguration(String modId, String name, ConfigEnv environment, float defaultValue) {
        super(modId, name, new net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration(defaultValue, environment.toSettings()));
    }
}
