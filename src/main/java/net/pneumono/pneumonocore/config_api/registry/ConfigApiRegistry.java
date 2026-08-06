package net.pneumono.pneumonocore.config_api.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import net.pneumono.pneumonocore.test.PneumonoCoreTestConfigs;
import net.pneumono.pneumonocore.util.MultiVersionUtil;

//? if >=1.20.5 {
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.pneumono.pneumonocore.datagen.ConfigResourceCondition;
import net.pneumono.pneumonocore.config_api.ConfigSyncPayload;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
//?}

//? if <1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerPlayer;
*///?}

import java.util.List;

public class ConfigApiRegistry {
    public static final Identifier CONFIG_SYNC_ID = PneumonoCore.location("config_sync");

    //? if >=1.20.5 {
    public static final ResourceConditionType<ConfigResourceCondition> RESOURCE_CONDITION_CONFIGURATIONS = ResourceConditionType.create(
            PneumonoCore.location("configurations"),
            ConfigResourceCondition.CODEC
    );
    //?}

    public static void register() {
        //? if >=1.20.5 {
        PayloadTypeRegistry./*? if >=26.1 {*/clientboundPlay()/*?} else {*//*playS2C()*//*?}*/
                .register(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        PayloadTypeRegistry./*? if >=26.1 {*/serverboundPlay()/*?} else {*//*playC2S()*//*?}*/
                .register(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC);
        ResourceConditions.register(RESOURCE_CONDITION_CONFIGURATIONS);
        //?}

        //? if >=1.20.5 {
        ServerPlayNetworking.registerGlobalReceiver(ConfigSyncPayload.TYPE, ConfigApiRegistry::receiveSyncPacket);
        //?} else {
        /*ServerPlayNetworking.registerGlobalReceiver(ConfigApiRegistry.CONFIG_SYNC_ID, ConfigApiRegistry::receiveSyncPacket);
        *///?}

        ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> ConfigApi.sendConfigSyncPacket(List.of(handler.getPlayer())));
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigApi.reloadValuesFromFiles(server, LoadType.RESTART);
        });
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
            ConfigApi.reloadValuesFromFiles(server, LoadType.RELOAD);
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            PneumonoCoreTestConfigs.registerTestConfigs();
            ServerConfigCommandRegistry.registerServerConfigCommand(PneumonoCoreTestConfigs.MOD_ID, "servertestconfigs");
        }
    }

    public static void receiveSyncPacket(
    //? if >=1.20.5 {
            ConfigSyncPayload payload,
            ServerPlayNetworking.Context context
    ) {
        if (ConfigApi.canEditServerConfigs(context.player())) {
            receiveSyncPacket(context.player().level().getServer(), payload.storedValues());
        }
    }
    //?} else {
            /*MinecraftServer server,
            ServerPlayer player,
            ServerPacketListener handler,
            FriendlyByteBuf buf,
            PacketSender responseSender
    ) {
        receiveSyncPacket(server, buf.readNbt());
    }
    *///?}

    public static void receiveSyncPacket(MinecraftServer server, CompoundTag nbt) {
        ConfigApi.LOGGER.info("Received config sync packet");

        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            CompoundTag compound = MultiVersionUtil.getCompound(nbt, configFile.getModId());
            if (compound == null || compound.isEmpty()) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                Tag element = compound.get(configuration.info().getName());
                if (element == null) continue;

                if (!setReceivedValue(server, configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.info().getId());
                }
            }
        }
    }

    private static  <T> boolean setReceivedValue(MinecraftServer server, AbstractConfiguration<T> config, Tag tag) {
        DataResult<Pair<T, Tag>> result = config.getValueCodec().decode(NbtOps.INSTANCE, tag);
        if (MultiVersionUtil.resultIsError(result)) {
            return false;
        }

        ConfigManager.setValue(config, MultiVersionUtil.resultGetOrThrow(result).getFirst(), LoadType.INSTANT, server);
        return true;
    }
}
