package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class BoundedIntegerConfiguration extends BoundedConfiguration<Integer> {
    public BoundedIntegerConfiguration(int defaultValue, int minValue, int maxValue, Settings settings) {
        super(defaultValue, minValue, maxValue, settings);
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.INT;
    }
}
