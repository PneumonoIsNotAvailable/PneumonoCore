package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config.configuration.BoundedFloatConfiguration;

@Deprecated
public class FloatConfiguration extends AbstractConfiguration<Float> {
    public FloatConfiguration(String modID, String name, ConfigEnv environment, float defaultValue) {
        super(name, new BoundedFloatConfiguration(defaultValue, 0, 1, createSettings(environment)));
    }
}
