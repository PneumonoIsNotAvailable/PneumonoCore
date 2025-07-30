package net.pneumono.pneumonocore.config_api.registry;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.pneumono.pneumonocore.datagen.ConfigResourceCondition;
import net.pneumono.pneumonocore.config_api.ConfigSyncS2CPayload;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import java.util.List;

public class ConfigApiRegistry {
    public static final Identifier CONFIG_SYNC_ID = PneumonoCore.identifier("config_sync");

    public static final ResourceConditionType<ConfigResourceCondition> RESOURCE_CONDITION_CONFIGURATIONS = ResourceConditionType.create(
            PneumonoCore.identifier("configurations"),
            ConfigResourceCondition.CODEC
    );

    public static void register() {
        PayloadTypeRegistry.playS2C().register(ConfigSyncS2CPayload.ID, ConfigSyncS2CPayload.CODEC);
        ResourceConditions.register(RESOURCE_CONDITION_CONFIGURATIONS);
        ServerConfigCommandRegistry.registerServerConfigCommand();
        ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> ConfigApi.sendConfigSyncPacket(List.of(handler.getPlayer())));
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
            ConfigApi.reloadValuesFromFiles(LoadType.RELOAD);
            ConfigApi.sendConfigSyncPacket(PlayerLookup.all(server));
        });
        //PneumonoCoreTestConfigs.registerTestConfigs();
    }
}
