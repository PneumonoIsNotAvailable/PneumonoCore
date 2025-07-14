package net.pneumono.pneumonocore.config;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.dynamic.Codecs;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Deprecated
public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    public EnumConfiguration(String modID, String name, ConfigEnv environment, T defaultValue) {
        super(name, new net.pneumono.pneumonocore.config.configuration.EnumConfiguration<>(defaultValue,
                createCodec(() -> defaultValue.getDeclaringClass().getEnumConstants()),
                createSettings(environment)
        ));
    }

    @Override
    public net.pneumono.pneumonocore.config.configuration.EnumConfiguration<T> getWrappedConfig() {
        return (net.pneumono.pneumonocore.config.configuration.EnumConfiguration<T>)super.getWrappedConfig();
    }

    // Simplified version of StringIdentifiable codec stuff
    private static <E extends Enum<E>> EnumCodec<E> createCodec(Supplier<E[]> enumValues) {
        E[] constants = enumValues.get();
        return new EnumCodec<>(constants, createMapper(constants));
    }

    private static <E extends Enum<E>> Function<String, E> createMapper(E[] values) {
        if (values.length > 16) {
            Map<String, E> map = Arrays.stream(values)
                    .collect(Collectors.toMap(Enum::name, value -> value));
            return name -> name == null ? null : map.get(name);
        } else {
            return name -> {
                for (E constant : values) {
                    if (constant.name().equals(name)) {
                        return constant;
                    }
                }

                return null;
            };
        }
    }

    public static class EnumCodec<E extends Enum<E>> implements Codec<E> {
        private final Codec<E> codec;

        public EnumCodec(E[] values, Function<String, E> idToConstant) {
            this.codec = Codecs.orCompressed(
                    Codec.stringResolver(Enum::name, idToConstant),
                    Codecs.rawIdChecked(Enum::ordinal, ordinal -> ordinal >= 0 && ordinal < values.length ? values[ordinal] : null, -1)
            );
        }

        @Override
        public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
            return this.codec.decode(ops, input);
        }

        public <T> DataResult<T> encode(E constant, DynamicOps<T> dynamicOps, T object) {
            return this.codec.encode(constant, dynamicOps, object);
        }
    }
}
