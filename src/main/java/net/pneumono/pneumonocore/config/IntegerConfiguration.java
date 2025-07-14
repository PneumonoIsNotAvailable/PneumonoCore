package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config.configuration.BoundedIntegerConfiguration;

@Deprecated
public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    public IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue) {
        super(name, new BoundedIntegerConfiguration(defaultValue, minValue, maxValue, createSettings(environment)));
    }
}
