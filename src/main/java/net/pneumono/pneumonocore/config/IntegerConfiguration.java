package net.pneumono.pneumonocore.config;

import net.minecraft.util.math.MathHelper;

@Deprecated
public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    public IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, minValue, maxValue));
    }
}
