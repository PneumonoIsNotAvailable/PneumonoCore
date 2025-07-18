package net.pneumono.pneumonocore.config_api.registry;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pneumono.pneumonocore.PneumonoCore;

public record ConfigPayload(NbtCompound compound) implements CustomPayload {
    public static final Id<ConfigPayload> ID = new Id<>(PneumonoCore.CONFIG_SYNC_ID);
    public static final PacketCodec<RegistryByteBuf, ConfigPayload> CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND, ConfigPayload::compound, ConfigPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
