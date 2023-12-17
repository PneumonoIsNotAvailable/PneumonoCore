package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.util.math.MathHelper;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    /**
     * Value is inclusive.
     */
    private final int minValue;
    /**
     * Value is inclusive.
     */
    private final int maxValue;

    public IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, minValue, maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    protected IntegerConfiguration(String modID, String name, ConfigEnv environment, int minValue, int maxValue, Integer defaultValue, Integer loadedValue) {
        super(modID, name, environment, MathHelper.clamp(defaultValue, minValue, maxValue), MathHelper.clamp(loadedValue, minValue, maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    protected IntegerConfiguration fromElement(JsonElement element) {
        return new IntegerConfiguration(modID, name, environment, minValue, maxValue, getDefaultValue(), element.getAsInt());
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
