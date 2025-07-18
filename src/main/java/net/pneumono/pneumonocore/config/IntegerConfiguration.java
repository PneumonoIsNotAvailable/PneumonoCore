package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class IntegerConfiguration extends AbstractConfiguration<Integer, net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration> {
    public IntegerConfiguration(String modId, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue) {
        super(modId, name, new net.pneumono.pneumonocore.config_api.configurations.IntegerConfiguration(defaultValue, minValue, maxValue, environment.toNew()));
    }

    public int getMinValue() {
        return getWrappedConfiguration().getMinValue();
    }

    public int getMaxValue() {
        return getWrappedConfiguration().getMaxValue();
    }
}
