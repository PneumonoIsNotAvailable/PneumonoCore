package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config_api.configurations.BoundedFloatConfiguration;

/**
 * @deprecated Use {@link BoundedFloatConfiguration} instead.
 */
@Deprecated
public class FloatConfiguration extends AbstractConfiguration<Float, BoundedFloatConfiguration> {
    public FloatConfiguration(String modId, String name, ConfigEnv environment, float defaultValue) {
        super(modId, name, new BoundedFloatConfiguration(defaultValue, environment.toSettings()));
    }
}
