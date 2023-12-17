package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.config.ClientConfigCommandRegistry;
import net.pneumono.pneumonocore.networking.ConfigSyncS2CPacket;

public class PneumonoCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Config
		ClientPlayNetworking.registerGlobalReceiver(PneumonoCore.CONFIG_SYNC_ID, ConfigSyncS2CPacket::receive);
		ClientConfigCommandRegistry.registerClientConfigCommand();
	}
}