package net.pneumono.pneumonocore.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryOps;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MultiVersionUtil {
    public static World getWorld(Entity entity) {
        //? if >= 1.21.9 {
        return entity.getEntityWorld();
        //?} else {
        /*return entity.getWorld();
        *///?}
    }

    public static <T> void putObjectWithCodec(DynamicRegistryManager registryManager, NbtCompound compound, String key, Codec<T> codec, T object) {
        putObjectWithCodec(RegistryOps.of(NbtOps.INSTANCE, registryManager), compound, key, codec, object);
    }

    public static <T> void putObjectWithCodec(NbtCompound compound, String key, Codec<T> codec, T object) {
        putObjectWithCodec(NbtOps.INSTANCE, compound, key, codec, object);
    }

    public static <T> void putObjectWithCodec(DynamicOps<NbtElement> ops, NbtCompound compound, String key, Codec<T> codec, T object) {
        //? if >=1.21.5 {
        compound.put(key, codec, ops, object);
        //?} else if >=1.20.6 {
        /*compound.put(key, codec.encodeStart(ops, object).getOrThrow());
        *///?} else {
        /*compound.put(key, codec.encodeStart(ops, object).result().orElseThrow());
        *///?}
    }

    public static @Nullable NbtCompound getCompound(NbtCompound compound, String key) {
        //? if >=1.21.5 {
        return compound.getCompound(key).orElse(null);
        //?} else {
        /*return compound.getCompound(key);
        *///?}
    }

    public static boolean resultIsError(DataResult<?> result) {
        //? if >=1.20.6 {
        return result.isError();
         //?} else {
        /*return result.error().isPresent();
        *///?}
    }

    public static <T> T resultGetOrThrow(DataResult<T> result) {
        //? if >=1.20.6 {
        return result.getOrThrow();
        //?} else {
        /*return result.result().orElseThrow();
        *///?}
    }

    public static <T> T getFirst(List<T> list) {
        //? if >=1.20.6 {
        return list.getFirst();
        //?} else {
        /*return list.get(0);
        *///?}
    }
}
