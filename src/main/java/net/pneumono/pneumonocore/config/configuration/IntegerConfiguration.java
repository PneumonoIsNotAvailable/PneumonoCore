package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    public IntegerConfiguration(int defaultValue, Settings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.INT;
    }
}
