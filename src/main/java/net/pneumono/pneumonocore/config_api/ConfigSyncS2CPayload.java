package net.pneumono.pneumonocore.config_api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;

import java.util.Collection;

public record ConfigSyncS2CPayload(NbtCompound storedValues) implements CustomPayload {
    public static final Id<ConfigSyncS2CPayload> ID = new Id<>(PneumonoCore.CONFIG_SYNC_ID);
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
            compound.put(configFile.getModID(), configFile.toNbt());
        }
        return compound;
    }

    public void updateConfigs() {
        ConfigApi.LOGGER.info("Received config sync packet");
        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            NbtCompound compound = storedValues.getCompound(configFile.getModID()).orElse(null);
            if (compound == null) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                NbtElement element = compound.get(configuration.getName());
                if (!setConfigValue(configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.getId());
                }
            }
        }
    }

    private static  <T> boolean setConfigValue(AbstractConfiguration<T> config, NbtElement jsonElement) {
        if (jsonElement == null) return false;

        DataResult<Pair<T, NbtElement>> result = config.getValueCodec().decode(NbtOps.INSTANCE, jsonElement);
        if (result.isError()) {
            return false;
        }
        ConfigManager.setSyncedValue(config, result.getOrThrow().getFirst());
        return true;
    }
}
