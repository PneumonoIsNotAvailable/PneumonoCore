package net.pneumono.pneumonocore.util;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//? if <1.21.5 {
/*import com.mojang.datafixers.util.Pair;
 *///?}

//? if >=1.20.5 {
import net.minecraft.world.item.component.ResolvableProfile;
//?}

@SuppressWarnings("unused")
public class MultiVersionUtil {
    @Deprecated
    public static Level getWorld(Entity entity) {
        return entity.level();
    }

    public static <T> void putObjectWithCodec(RegistryAccess registryAccess, CompoundTag compound, String key, Codec<T> codec, T object) {
        putObjectWithCodec(RegistryOps.create(NbtOps.INSTANCE, registryAccess), compound, key, codec, object);
    }

    public static <T> void putObjectWithCodec(CompoundTag compound, String key, Codec<T> codec, T object) {
        putObjectWithCodec(NbtOps.INSTANCE, compound, key, codec, object);
    }

    public static <T> void putObjectWithCodec(DynamicOps<Tag> ops, CompoundTag compound, String key, Codec<T> codec, T object) {
        //? if >=1.21.5 {
        compound.store(key, codec, ops, object);
        //?} else if >=1.20.5 {
        /*compound.put(key, codec.encodeStart(ops, object).getOrThrow());
        *///?} else {
        /*compound.put(key, codec.encodeStart(ops, object).result().orElseThrow());
        *///?}
    }

    public static <T> Optional<T> getObjectWithCodec(RegistryAccess registryAccess, CompoundTag nbt, String key, Codec<T> codec) {
        return getObjectWithCodec(RegistryOps.create(NbtOps.INSTANCE, registryAccess), nbt, key, codec);
    }

    public static <T> Optional<T> getObjectWithCodec(CompoundTag nbt, String key, Codec<T> codec) {
        return getObjectWithCodec(NbtOps.INSTANCE, nbt, key, codec);
    }

    @SuppressWarnings("unused")
    public static <T> Optional<T> getObjectWithCodec(DynamicOps<Tag> ops, CompoundTag nbt, String key, Codec<T> codec) {
        //? if >=1.21.5 {
        return nbt.read(key, codec);
        //?} else {
        /*return codec.decode(ops, nbt.get(key)).result().map(Pair::getFirst);
         *///?}
    }

    public static @Nullable CompoundTag getCompound(CompoundTag compound, String key) {
        //? if >=1.21.5 {
        return compound.getCompound(key).orElse(null);
        //?} else {
        /*if (compound.contains(key)) {
            return compound.getCompound(key);
        } else {
            return null;
        }
        *///?}
    }

    public static CompoundTag getCompoundOrEmpty(CompoundTag nbt, String key) {
        //? if >=1.21.5 {
        return nbt.getCompoundOrEmpty(key);
        //?} else {
        /*return nbt.getCompound(key);
         *///?}
    }

    public static ListTag getCompoundListOrEmpty(CompoundTag nbt, String key) {
        //? if >=1.21.5 {
        return nbt.getListOrEmpty(key);
        //?} else {
        /*return nbt.getList(key, Tag.TAG_COMPOUND);
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
    public static GameProfile getGameProfile(ResolvableProfile profile) {
        //? if >=1.21.9 {
        return profile.partialProfile();
        //?} else {
        /*return profile.gameProfile();
         *///?}
    }
    //?}

    public static GlobalPos createGlobalPos(ResourceKey<Level> dimension, BlockPos pos) {
        //? if >=1.20.5 {
        return new GlobalPos(dimension, pos);
        //?} else {
        /*return GlobalPos.of(dimension, pos);
         *///?}
    }

    @Deprecated
    public static BlockPos getPos(GlobalPos pos) {
        return pos.pos();
    }

    @Deprecated
    public static ResourceKey<Level> getDimension(GlobalPos pos) {
        return pos.dimension();
    }
}
