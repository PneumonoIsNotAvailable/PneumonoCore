package net.pneumono.pneumonocore.util;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//? if <1.21.5 {
/*import com.mojang.datafixers.util.Pair;
 *///?}

//? if >=1.20.5 {
import net.minecraft.component.type.ProfileComponent;
//?}

@SuppressWarnings("unused")
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
        //?} else if >=1.20.5 {
        /*compound.put(key, codec.encodeStart(ops, object).getOrThrow());
        *///?} else {
        /*compound.put(key, codec.encodeStart(ops, object).result().orElseThrow());
        *///?}
    }

    public static <T> Optional<T> getObjectWithCodec(DynamicRegistryManager registryManager, NbtCompound nbt, String key, Codec<T> codec) {
        return getObjectWithCodec(RegistryOps.of(NbtOps.INSTANCE, registryManager), nbt, key, codec);
    }

    public static <T> Optional<T> getObjectWithCodec(NbtCompound nbt, String key, Codec<T> codec) {
        return getObjectWithCodec(NbtOps.INSTANCE, nbt, key, codec);
    }

    @SuppressWarnings("unused")
    public static <T> Optional<T> getObjectWithCodec(DynamicOps<NbtElement> ops, NbtCompound nbt, String key, Codec<T> codec) {
        //? if >=1.21.5 {
        return nbt.get(key, codec);
        //?} else {
        /*return codec.decode(ops, nbt.get(key)).result().map(Pair::getFirst);
         *///?}
    }

    public static @Nullable NbtCompound getCompound(NbtCompound compound, String key) {
        //? if >=1.21.5 {
        return compound.getCompound(key).orElse(null);
        //?} else {
        /*return compound.getCompound(key);
        *///?}
    }

    public static NbtCompound getCompoundOrEmpty(NbtCompound nbt, String key) {
        //? if >=1.21.5 {
        return nbt.getCompoundOrEmpty(key);
        //?} else {
        /*return nbt.getCompound(key);
         *///?}
    }

    public static NbtList getCompoundListOrEmpty(NbtCompound nbt, String key) {
        //? if >=1.21.5 {
        return nbt.getListOrEmpty(key);
        //?} else {
        /*return nbt.getList(key, NbtElement.COMPOUND_TYPE);
         *///?}
    }

    public static boolean resultIsError(DataResult<?> result) {
        //? if >=1.20.5 {
        return result.isError();
         //?} else {
        /*return result.error().isPresent();
        *///?}
    }

    public static <T> T resultGetOrThrow(DataResult<T> result) {
        //? if >=1.20.5 {
        return result.getOrThrow();
        //?} else {
        /*return result.result().orElseThrow();
        *///?}
    }

    public static <T> T getFirst(List<T> list) {
        //? if >=1.20.5 {
        return list.getFirst();
        //?} else {
        /*return list.get(0);
        *///?}
    }

    public static UUID getId(GameProfile profile) {
        //? if >=1.21.9 {
        return profile.id();
        //?} else {
        /*return profile.getId();
         *///?}
    }

    public static String getName(GameProfile profile) {
        //? if >=1.21.9 {
        return profile.name();
        //?} else {
        /*return profile.getName();
         *///?}
    }

    //? if >=1.20.5 {
    public static GameProfile getGameProfile(ProfileComponent profile) {
        //? if >=1.21.9 {
        return profile.getGameProfile();
        //?} else {
        /*return profile.gameProfile();
         *///?}
    }
    //?}

    public static GlobalPos createGlobalPos(RegistryKey<World> dimension, BlockPos pos) {
        //? if >=1.20.5 {
        return new GlobalPos(dimension, pos);
        //?} else {
        /*return GlobalPos.create(dimension, pos);
         *///?}
    }

    public static BlockPos getPos(GlobalPos pos) {
        //? if >=1.20.5 {
        return pos.pos();
        //?} else {
        /*return pos.getPos();
         *///?}
    }

    public static RegistryKey<World> getDimension(GlobalPos pos) {
        //? if >=1.20.5 {
        return pos.dimension();
        //?} else {
        /*return pos.getDimension();
         *///?}
    }
}
