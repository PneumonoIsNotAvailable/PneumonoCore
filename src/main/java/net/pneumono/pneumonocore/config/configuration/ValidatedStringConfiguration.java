package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

import java.util.function.Predicate;

public class ValidatedStringConfiguration extends AbstractConfiguration<String> {
    public ValidatedStringConfiguration(String defaultValue, Predicate<String> validator, Settings settings) {
        super(defaultValue, validator, settings);
    }

    @Override
    public Codec<String> getValueCodec() {
        return Codec.STRING;
    }
}
