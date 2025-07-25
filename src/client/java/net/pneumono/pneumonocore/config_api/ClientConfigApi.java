package net.pneumono.pneumonocore.config_api;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.ErroneousConfigurationEntry;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

import java.util.HashMap;
import java.util.Map;

public final class ClientConfigApi {
    private static final Map<Identifier, EntryFactory> CONFIG_ENTRY_TYPES = new HashMap<>();

    /**
     * Registers a builder for a class extending {@link AbstractConfigurationEntry}.
     *
     * <p>The identifier should be the same one returned by {@link AbstractConfiguration.Info#getConfigTypeId()}.
     * You most likely need to cast the abstract configuration to your preferred subclass,
     * this can be done using an instanceof check and returning null otherwise.
     *
     * <p>Returning null is completely fine,
     * and the config screen will simply use an {@linkplain ErroneousConfigurationEntry erroneous entry} in its place.
     *
     * <p>See {@link ClientConfigApiRegistry} for examples.
     */
    public static void registerConfigEntryType(Identifier id, EntryFactory builder) {
        CONFIG_ENTRY_TYPES.put(id, builder);
    }

    public static EntryFactory getConfigEntryType(Identifier id) {
        return CONFIG_ENTRY_TYPES.getOrDefault(id, (EntryFactory) ErroneousConfigurationEntry::new);
    }

    @FunctionalInterface
    public interface EntryFactory {
        AbstractConfigurationEntry<?, ?> build(ConfigOptionsScreen parent, ConfigsListWidget widget, AbstractConfiguration<?> configuration);
    }
}
