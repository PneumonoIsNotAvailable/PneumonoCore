package net.pneumono.pneumonocore.config_api;

import net.minecraft.nbt.CompoundTag;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.util.MultiVersionUtil;
import java.util.Collection;

//? if >=1.20.5 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import org.jetbrains.annotations.NotNull;
//?}

/**
 * Only used as an actual payload >=1.20.5
 */
public record ConfigSyncS2CPayload(CompoundTag storedValues) /*? if >=1.20.5 {*/implements CustomPacketPayload/*?}*/ {
    //? if >=1.20.5 {
    public static final Type<ConfigSyncS2CPayload> TYPE = new Type<>(ConfigApiRegistry.CONFIG_SYNC_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigSyncS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            ConfigSyncS2CPayload::storedValues,
            ConfigSyncS2CPayload::new
    );

    @Override
    public @NotNull Type<? extends ConfigSyncS2CPayload> type() {
        return TYPE;
    }

    public ConfigSyncS2CPayload(Collection<ConfigFile> configFiles) {
        this(toNbt(configFiles));
    }
    //?}

    public static CompoundTag toNbt(Collection<ConfigFile> configFiles) {
        CompoundTag compound = new CompoundTag();
        for (ConfigFile configFile : configFiles) {

            CompoundTag fileNbt = new CompoundTag();
            for (AbstractConfiguration<?> config : configFile.getConfigurations()) {
                putConfigValue(fileNbt, config);
            }
            compound.put(configFile.getModId(), fileNbt);
        }
        return compound;
    }

    private static <T> void putConfigValue(CompoundTag compound, AbstractConfiguration<T> config) {
        MultiVersionUtil.putObjectWithCodec(compound, config.info().getName(), config.getValueCodec(), config.getValue());
    }
}
