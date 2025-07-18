package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.StringConfiguration} instead.
 */
@Deprecated
public class StringConfiguration extends AbstractConfiguration<String, net.pneumono.pneumonocore.config_api.configurations.StringConfiguration> {
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(new net.pneumono.pneumonocore.config_api.configurations.StringConfiguration(modID, name, environment.toNew(), defaultValue));
    }
}
