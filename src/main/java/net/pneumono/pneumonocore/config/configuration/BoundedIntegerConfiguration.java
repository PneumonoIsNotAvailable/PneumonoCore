package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public class BoundedIntegerConfiguration extends AbstractConfiguration<Integer> {
    private final int minValue;
    private final int maxValue;

    public BoundedIntegerConfiguration(int defaultValue, int minValue, int maxValue, Settings settings) {
        super(defaultValue, getValidator(minValue, maxValue), settings);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static Predicate<Integer> getValidator(int minValue, int maxValue) {
        if (minValue > maxValue) throw new IllegalArgumentException("minValue cannot be larger than maxValue!");
        return value -> value >= minValue && value <= maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.INT;
    }
}
