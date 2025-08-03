package net.pneumono.pneumonocore.config_api.registry;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;

import java.util.List;

public class ConfigApiRegistry {
    public static final Identifier CONFIG_SYNC_ID = PneumonoCore.identifier("config_sync");

    public static void register() {
        ServerConfigCommandRegistry.registerServerConfigCommand();
        ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> ConfigApi.sendConfigSyncPacket(List.of(handler.getPlayer())));
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
            ConfigApi.reloadValuesFromFiles(LoadType.RELOAD);
            ConfigApi.sendConfigSyncPacket(PlayerLookup.all(server));
        });
        //PneumonoCoreTestConfigs.registerTestConfigs();
    }
}
