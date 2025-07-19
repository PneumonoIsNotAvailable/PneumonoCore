package net.pneumono.pneumonocore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.config_api.ClientConfigApi;
import net.pneumono.pneumonocore.config_api.ClientConfigCommandRegistry;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.entries.*;
import net.pneumono.pneumonocore.config_api.ConfigSyncS2CPayload;

public class PneumonoCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Config
		ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.ID, (payload, context) -> payload.updateConfigs());
		ClientConfigCommandRegistry.registerClientConfigCommand();
		registerConfigEntryType("boolean", (parent, widget, configuration) ->
				configuration instanceof BooleanConfiguration booleanConfiguration ?
						new BooleanConfigurationEntry(parent, widget, booleanConfiguration) : null
		);
		registerConfigEntryType("enum", (parent, widget, configuration) ->
				configuration instanceof EnumConfiguration<?> enumConfiguration ?
						new EnumConfigurationEntry<>(parent, widget, enumConfiguration) : null
		);
		registerConfigEntryType("integer", (parent, widget, configuration) ->
				configuration instanceof IntegerConfiguration integerConfiguration ?
						new IntegerConfigurationEntry(parent, widget, integerConfiguration) : null
		);
		registerConfigEntryType("float", (parent, widget, configuration) ->
				configuration instanceof FloatConfiguration floatConfiguration ?
						new FloatConfigurationEntry(parent, widget, floatConfiguration) : null
		);
		registerConfigEntryType("string", (parent, widget, configuration) ->
				configuration instanceof StringConfiguration stringConfiguration ?
						new StringConfigurationEntry(parent, widget, stringConfiguration) : null
		);
		registerConfigEntryType("time", (parent, widget, configuration) ->
				configuration instanceof TimeConfiguration timeConfiguration ?
						new TimeConfigurationEntry(parent, widget, timeConfiguration) : null
		);
	}

	private static void registerConfigEntryType(String name, ClientConfigApi.EntryFactory selector) {
		ClientConfigApi.registerConfigEntryType(PneumonoCore.identifier(name), selector);
	}
}