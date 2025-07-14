package net.pneumono.pneumonocore.config.configuration;

import java.util.function.Predicate;

public abstract class BoundedConfiguration<T extends Number> extends AbstractConfiguration<T> {
    private final T minValue;
    private final T maxValue;

    public BoundedConfiguration(T defaultValue, T minValue, T maxValue, Settings settings) {
        super(defaultValue, getValidator(minValue, maxValue), settings);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static <T extends Number> Predicate<T> getValidator(T minValue, T maxValue) {
        double min = minValue.doubleValue();
        double max = maxValue.doubleValue();
        if (min > max) throw new IllegalArgumentException("minValue cannot be larger than maxValue!");
        return value -> value.doubleValue() >= min && value.doubleValue() <= max;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }
}
