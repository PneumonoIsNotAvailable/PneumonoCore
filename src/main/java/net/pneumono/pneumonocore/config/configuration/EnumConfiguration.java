package net.pneumono.pneumonocore.config.configuration;

import com.mojang.serialization.Codec;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Codec<T> codec;

    public EnumConfiguration(T defaultValue, Codec<T> codec, Settings settings) {
        super(defaultValue, settings);
        this.codec = codec;
    }

    @Override
    public Codec<T> getValueCodec() {
        return codec;
    }
}
