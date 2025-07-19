package net.pneumono.pneumonocore.config_api;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config_api.entries.ErroneousConfigurationEntry;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;

import java.util.HashMap;
import java.util.Map;

public class ClientConfigApi {
    private static final Map<Identifier, EntryFactory> CONFIG_ENTRY_TYPES = new HashMap<>();

    public static void registerConfigEntryType(Identifier id, EntryFactory builder) {
        CONFIG_ENTRY_TYPES.put(id, builder);
    }

    public static EntryFactory getConfigEntryType(Identifier id) {
        return CONFIG_ENTRY_TYPES.getOrDefault(id, (EntryFactory) ErroneousConfigurationEntry::new);
    }

    public static AbstractConfigurationEntry<?, ?> createErroneousEntry(ConfigOptionsScreen parent, ConfigsListWidget widget, AbstractConfiguration<?> configuration) {
        return new ErroneousConfigurationEntry<>(parent, widget, configuration);
    }

    public interface EntryFactory {
        AbstractConfigurationEntry<?, ?> build(ConfigOptionsScreen parent, ConfigsListWidget widget, AbstractConfiguration<?> configuration);
    }
}
