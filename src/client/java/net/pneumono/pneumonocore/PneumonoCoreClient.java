package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.ClientConfigCommandRegistry;
import net.pneumono.pneumonocore.config_api.entries.*;
import net.pneumono.pneumonocore.config_api.ConfigSyncS2CPayload;

public class PneumonoCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Config

		ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.ID, (payload, context) -> payload.updateConfigs());
		ClientConfigCommandRegistry.registerClientConfigCommand();
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("boolean"), BooleanConfigurationEntry::new);
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("enum"), EnumConfigurationEntry::new);
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("integer"), IntegerConfigurationEntry::new);
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("float"), FloatConfigurationEntry::new);
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("string"), StringConfigurationEntry::new);
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier("time"), TimeConfigurationEntry::new);
	}
}