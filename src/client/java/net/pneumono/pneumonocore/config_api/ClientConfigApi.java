package net.pneumono.pneumonocore.config_api;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ClientConfigApi {
    private static final Map<Identifier, ConfigsListWidget.EntryBuilder> CONFIG_ENTRY_TYPES = new HashMap<>();

    public static void registerConfigEntryType(Identifier id, ConfigsListWidget.EntryBuilder builder) {
        CONFIG_ENTRY_TYPES.put(id, builder);
    }

    public static ConfigsListWidget.EntryBuilder getConfigEntryType(Identifier id) {
        return CONFIG_ENTRY_TYPES.get(id);
    }
}
