package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration} instead.
 */
@Deprecated
public class FloatConfiguration extends AbstractConfiguration<Float, net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration> {
    public FloatConfiguration(String modID, String name, ConfigEnv environment, float defaultValue) {
        super(new net.pneumono.pneumonocore.config_api.configurations.FloatConfiguration(modID, name, defaultValue, environment.toNew()));
    }
}
