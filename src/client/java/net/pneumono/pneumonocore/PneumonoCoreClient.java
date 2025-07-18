package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.ClientConfigCommandRegistry;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.entries.*;
import net.pneumono.pneumonocore.config_api.ConfigSyncS2CPayload;

public class PneumonoCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Config
		ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.ID, (payload, context) -> payload.updateConfigs());
		ClientConfigCommandRegistry.registerClientConfigCommand();
		registerConfigEntryType("boolean", BooleanConfigurationEntry::new);
		registerConfigEntryType("enum", EnumConfigurationEntry::new);
		registerConfigEntryType("integer", IntegerConfigurationEntry::new);
		registerConfigEntryType("float", FloatConfigurationEntry::new);
		registerConfigEntryType("string", StringConfigurationEntry::new);
		registerConfigEntryType("time", TimeConfigurationEntry::new);
	}

	private static void registerConfigEntryType(String name, ConfigsListWidget.EntryBuilder builder) {
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier(name), builder);
	}
}