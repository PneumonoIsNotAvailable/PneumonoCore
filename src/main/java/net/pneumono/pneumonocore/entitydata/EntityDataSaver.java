package net.pneumono.pneumonocore.entitydata;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Uuids;

import java.util.UUID;

@SuppressWarnings("unused")
public interface EntityDataSaver {
    NbtCompound getModdedData();

    default void putModdedElement(String key, NbtElement value) {
        getModdedData().put(key, value);
    }

    default void putModdedBoolean(String key, boolean value) {
        getModdedData().putBoolean(key, value);
    }

    default void putModdedString(String key, String value) {
        getModdedData().putString(key, value);
    }

    default void putModdedInt(String key, int value) {
        getModdedData().putInt(key, value);
    }

    default void putModdedLong(String key, long value) {
        getModdedData().putLong(key, value);
    }

    default void putModdedUuid(String key, UUID value) {
        getModdedData().put(key, Uuids.INT_STREAM_CODEC, value);
    }

    default void putModdedByte(String key, byte value) {
        getModdedData().putByte(key, value);
    }

    default void putModdedByteArray(String key, byte[] value) {
        getModdedData().putByteArray(key, value);
    }

    default void putModdedDouble(String key, double value) {
        getModdedData().putDouble(key, value);
    }

    default void putModdedFloat(String key, float value) {
        getModdedData().putFloat(key, value);
    }

    default void putModdedIntArray(String key, int[] value) {
        getModdedData().putIntArray(key, value);
    }

    default void putModdedLongArray(String key, long[] value) {
        getModdedData().putLongArray(key, value);
    }

    default void putModdedShort(String key, short value) {
        getModdedData().putShort(key, value);
    }
}
