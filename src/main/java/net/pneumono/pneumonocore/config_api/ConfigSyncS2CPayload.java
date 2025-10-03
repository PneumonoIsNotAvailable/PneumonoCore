package net.pneumono.pneumonocore.config_api;

import net.minecraft.nbt.NbtCompound;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.util.MultiVersionUtil;
import java.util.Collection;

//? if >=1.20.6 {
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
//?}

/**
 * Only used as an actual payload >=1.20.6
 */
public record ConfigSyncS2CPayload(NbtCompound storedValues) /*? if >=1.20.6 {*/implements CustomPayload/*?}*/ {
    //? if >=1.20.6 {
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
    //?}

    public static NbtCompound toNbt(Collection<ConfigFile> configFiles) {
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
        MultiVersionUtil.putObjectWithCodec(compound, config.info().getName(), config.getValueCodec(), config.getValue());
    }
}
