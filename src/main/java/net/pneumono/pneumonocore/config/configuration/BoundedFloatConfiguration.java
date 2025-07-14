package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class BoundedFloatConfiguration extends BoundedConfiguration<Float> {
    public BoundedFloatConfiguration(float defaultValue, float minValue, float maxValue, Settings settings) {
        super(defaultValue, minValue, maxValue, settings);
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.FLOAT;
    }
}
