package net.pneumono.pneumonocore.config_api;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.*;
import net.pneumono.pneumonocore.config_api.screen.entries.*;

public class ClientConfigApiRegistry {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncS2CPayload.ID, (payload, context) -> ClientConfigApi.receiveSyncPacket(payload));

        ClientConfigCommandRegistry.registerClientConfigCommand();

        // Scuffed as hell but couldn't find a better way of doing that so whatever
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
