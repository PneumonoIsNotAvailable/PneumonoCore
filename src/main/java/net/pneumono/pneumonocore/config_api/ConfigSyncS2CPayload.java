package net.pneumono.pneumonocore.config_api;

//? if >=1.20.6 {
/*import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;

import java.util.Collection;

public record ConfigSyncS2CPayload(NbtCompound storedValues) implements CustomPayload {
    public static final Id<ConfigSyncS2CPayload> ID = new Id<>(ConfigApiRegistry.CONFIG_SYNC_ID);
    public static final PacketCodec<RegistryByteBuf, ConfigSyncS2CPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.NBT_COMPOUND,
            ConfigSyncS2CPayload::storedValues,
            ConfigSyncS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public ConfigSyncS2CPayload(Collection<ConfigFile> configFiles) {
        this(toNbt(configFiles));
    }

    private static NbtCompound toNbt(Collection<ConfigFile> configFiles) {
        NbtCompound compound = new NbtCompound();
        for (ConfigFile configFile : configFiles) {

            NbtCompound fileNbt = new NbtCompound();
            for (AbstractConfiguration<?> config : configFile.getConfigurations()) {
                putConfigValue(fileNbt, config);
            }
            compound.put(configFile.getModId(), fileNbt);
        }
        return compound;
    }

    private static <T> void putConfigValue(NbtCompound compound, AbstractConfiguration<T> config) {
        //? if >=1.21.8 {
        /^compound.put(config.info().getName(), config.getValueCodec(), config.getValue());
        ^///?} else {
        NbtElement element = config.getValueCodec().encodeStart(NbtOps.INSTANCE, config.getValue()).getOrThrow();
        compound.put(config.info().getName(), element);
        //?}
    }
}
*///?}