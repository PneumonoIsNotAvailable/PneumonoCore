package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class TimeConfiguration extends AbstractConfiguration<Long> {
    public TimeConfiguration(Long defaultValue, Settings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Long> getValueCodec() {
        return Codec.LONG;
    }
}
