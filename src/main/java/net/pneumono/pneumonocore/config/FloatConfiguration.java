package net.pneumono.pneumonocore.config;

import net.minecraft.util.math.MathHelper;

@Deprecated
public class FloatConfiguration extends AbstractConfiguration<Float> {
    public FloatConfiguration(String modID, String name, ConfigEnv environment, float defaultValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, 0F, 1F));
    }
}
