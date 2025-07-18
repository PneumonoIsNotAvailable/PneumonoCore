package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.config_api.ClientConfigCommandRegistry;
import net.pneumono.pneumonocore.config_api.ConfigsListWidget;
import net.pneumono.pneumonocore.config_api.entries.*;
import net.pneumono.pneumonocore.config_api.registry.ConfigPayload;
import net.pneumono.pneumonocore.config_api.registry.PackagedConfigs;

import java.util.HashMap;
import java.util.Map;

public class PneumonoCoreClient implements ClientModInitializer {
	public static Map<String, ConfigsListWidget.EntryBuilder> CONFIG_SCREEN_ENTRY_TYPES = new HashMap<>();

	@Override
	public void onInitializeClient() {
		// Config

		ClientPlayNetworking.registerGlobalReceiver(ConfigPayload.ID, (payload, context) -> new PackagedConfigs(payload.compound()).updateServerConfigs());
		ClientConfigCommandRegistry.registerClientConfigCommand();
		CONFIG_SCREEN_ENTRY_TYPES.put("BooleanConfiguration", BooleanConfigurationEntry::new);
		CONFIG_SCREEN_ENTRY_TYPES.put("EnumConfiguration", EnumConfigurationEntry::new);
		CONFIG_SCREEN_ENTRY_TYPES.put("IntegerConfiguration", IntegerConfigurationEntry::new);
		CONFIG_SCREEN_ENTRY_TYPES.put("FloatConfiguration", FloatConfigurationEntry::new);
		CONFIG_SCREEN_ENTRY_TYPES.put("StringConfiguration", StringConfigurationEntry::new);
		CONFIG_SCREEN_ENTRY_TYPES.put("TimeConfiguration", TimeConfigurationEntry::new);
	}
}