package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration} instead.
 */
@Deprecated
public class BooleanConfiguration extends AbstractConfiguration<Boolean, net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration> {
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue) {
        super(new net.pneumono.pneumonocore.config_api.configurations.BooleanConfiguration(modID, name, environment.toNew(), defaultValue));
    }
}
