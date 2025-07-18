package net.pneumono.pneumonocore.config_api.registry;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class PlayerJoinEvent implements ServerPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        // Send server configurations to client
        ConfigApi.sendS2CConfigSyncPacket(handler.getPlayer());
    }
}
