package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config_api.configurations.BoundedIntegerConfiguration;

/**
 * @deprecated Use {@link BoundedIntegerConfiguration} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class IntegerConfiguration extends AbstractConfiguration<Integer, BoundedIntegerConfiguration> {
    public IntegerConfiguration(String modId, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue) {
        super(modId, name, new BoundedIntegerConfiguration(defaultValue, minValue, maxValue, environment.toSettings()));
    }

    public int getMinValue() {
        return getWrappedConfiguration().getMinValue();
    }

    public int getMaxValue() {
        return getWrappedConfiguration().getMaxValue();
    }
}
