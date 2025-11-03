package net.pneumono.pneumonocore.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.ExtraCodecs;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CodecUtil {
    /**
     * Creates a codec for an enum.
     *
     * <p>{@link net.minecraft.util.StringRepresentable#fromEnum} should be used whenever possible,
     * but if the enum does not implement StringIdentifiable, then this can be used in instead.
     */
    public static <T extends Enum<T>> Codec<T> createEnumCodec(T[] enums) {
        Function<String, T> function;
        if (enums.length > 16) {
            Map<String, T> map = Arrays.stream(enums)
                    .collect(Collectors.toMap(Enum::name, value -> value));
            function = name -> name == null ? null : map.get(name);
        } else {
            function = name -> {
                for (T constant : enums) {
                    if (constant.name().equals(name)) {
                        return constant;
                    }
                }

                return null;
            };
        }

        return new EnumCodec<>(enums, function);
    }

    public static class EnumCodec<E extends Enum<E>> implements Codec<E> {
        private final Codec<E> codec;

        public EnumCodec(E[] values, Function<String, E> idToConstant) {
            //? if >=1.20.5 {
            this.codec = ExtraCodecs.orCompressed(
                    Codec.stringResolver(Enum::name, idToConstant),
                    ExtraCodecs.idResolverCodec(Enum::ordinal, ordinal -> ordinal >= 0 && ordinal < values.length ? values[ordinal] : null, -1)
            );
            //?} else {
            /*this.codec = ExtraCodecs.orCompressed(
                    ExtraCodecs.stringResolverCodec(Enum::name, idToConstant),
                    ExtraCodecs.idResolverCodec(Enum::ordinal, ordinal -> ordinal >= 0 && ordinal < values.length ? values[ordinal] : null, -1)
            );
            *///?}
        }

        @Override
        public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
            return this.codec.decode(ops, input);
        }

        public <T> DataResult<T> encode(E stringIdentifiable, DynamicOps<T> dynamicOps, T object) {
            return this.codec.encode(stringIdentifiable, dynamicOps, object);
        }
    }
}
