package net.pneumono.pneumonocore.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pneumono.pneumonocore.config.PackagedConfigs;

public class ConfigSyncS2CPacket {
    @SuppressWarnings("unused")
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Saves new SERVER_CONFIGS that have been received from the server
        new PackagedConfigs(buf.readString()).updateServerConfigs();
    }
}
