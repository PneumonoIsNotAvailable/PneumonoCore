package net.pneumono.pneumonocore.entitydata;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.UUID;

@SuppressWarnings("unused")
public interface EntityDataSaver {
    NbtCompound pneumonoCore$getModdedData();

    default void putModdedElement(String key, NbtElement value) {
        pneumonoCore$getModdedData().put(key, value);
    }

    default void putModdedBoolean(String key, boolean value) {
        pneumonoCore$getModdedData().putBoolean(key, value);
    }

    default void putModdedString(String key, String value) {
        pneumonoCore$getModdedData().putString(key, value);
    }

    default void putModdedInt(String key, int value) {
        pneumonoCore$getModdedData().putInt(key, value);
    }

    default void putModdedLong(String key, long value) {
        pneumonoCore$getModdedData().putLong(key, value);
    }

    default void putModdedUuid(String key, UUID value) {
        pneumonoCore$getModdedData().putUuid(key, value);
    }

    default void putModdedByte(String key, byte value) {
        pneumonoCore$getModdedData().putByte(key, value);
    }

    default void putModdedByteArray(String key, byte[] value) {
        pneumonoCore$getModdedData().putByteArray(key, value);
    }

    default void putModdedDouble(String key, double value) {
        pneumonoCore$getModdedData().putDouble(key, value);
    }

    default void putModdedFloat(String key, float value) {
        pneumonoCore$getModdedData().putFloat(key, value);
    }

    default void putModdedIntArray(String key, int[] value) {
        pneumonoCore$getModdedData().putIntArray(key, value);
    }

    default void putModdedLongArray(String key, long[] value) {
        pneumonoCore$getModdedData().putLongArray(key, value);
    }

    default void putModdedShort(String key, short value) {
        pneumonoCore$getModdedData().putShort(key, value);
    }
}
