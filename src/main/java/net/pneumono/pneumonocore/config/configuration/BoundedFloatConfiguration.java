package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public class BoundedFloatConfiguration extends AbstractConfiguration<Float> {
    private final float minValue;
    private final float maxValue;

    public BoundedFloatConfiguration(float defaultValue, float minValue, float maxValue, Settings settings) {
        super(defaultValue, getValidator(minValue, maxValue), settings);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static Predicate<Float> getValidator(float minValue, float maxValue) {
        if (minValue > maxValue) throw new IllegalArgumentException("minValue cannot be larger than maxValue!");
        return value -> value >= minValue && value <= maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.FLOAT;
    }
}
