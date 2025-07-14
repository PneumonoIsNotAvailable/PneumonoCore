package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class StringConfiguration extends AbstractConfiguration<String> {
    public StringConfiguration(String defaultValue, Settings settings) {
        super(defaultValue, value -> true, settings);
    }

    @Override
    public Codec<String> getValueCodec() {
        return Codec.STRING;
    }
}
