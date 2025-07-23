package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.pneumono.pneumonocore.config_api.ClientConfigApiRegistry;

public final class PneumonoCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientConfigApiRegistry.register();
	}
}