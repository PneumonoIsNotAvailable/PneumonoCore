package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class FloatConfiguration extends AbstractConfiguration<Float> {
    public FloatConfiguration(float defaultValue, Settings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.FLOAT;
    }
}
