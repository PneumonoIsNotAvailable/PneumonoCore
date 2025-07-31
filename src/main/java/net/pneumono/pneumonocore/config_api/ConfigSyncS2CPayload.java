package net.pneumono.pneumonocore.config_api;

import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;

import java.util.Collection;

public class ConfigSyncS2CPayload {
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
        DataResult<NbtElement> result = config.getValueCodec().encodeStart(NbtOps.INSTANCE, config.getValue());
        compound.put(config.info().getName(), result.result().isPresent() ? result.result().get() : new NbtCompound());
    }
}
