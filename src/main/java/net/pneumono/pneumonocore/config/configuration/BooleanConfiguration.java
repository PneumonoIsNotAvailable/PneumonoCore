package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    public BooleanConfiguration(boolean defaultValue, Settings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.BOOL;
    }
}
