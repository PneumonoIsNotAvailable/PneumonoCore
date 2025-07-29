package net.pneumono.pneumonocore.config_api;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;

import java.util.Collection;

//? if <=1.20.4 {
public final class ConfigSyncNetworking {
    public static PacketByteBuf write(Collection<ConfigFile> configFiles) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeNbt(toNbt(configFiles));
        return buf;
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
        NbtElement element = config.getValueCodec().encodeStart(NbtOps.INSTANCE, config.getValue()).result().orElseThrow();
        compound.put(config.info().getName(), element);
    }
}
//?}