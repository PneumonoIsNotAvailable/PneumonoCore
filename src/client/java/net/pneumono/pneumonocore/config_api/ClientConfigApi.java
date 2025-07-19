package net.pneumono.pneumonocore.config_api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.screen.entries.AbstractConfigurationEntry;
import net.pneumono.pneumonocore.config_api.screen.entries.ErroneousConfigurationEntry;
import net.pneumono.pneumonocore.config_api.screen.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config_api.screen.widgets.ConfigsListWidget;

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

    public static void receiveSyncPacket(ConfigSyncS2CPayload payload) {
        ConfigApi.LOGGER.info("Received config sync packet");

        for (ConfigFile configFile : ConfigApi.getConfigFiles()) {
            NbtCompound compound = payload.storedValues().getCompound(configFile.getModID()).orElse(null);
            if (compound == null) continue;

            for (AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
                NbtElement element = compound.get(configuration.info().getName());
                if (element == null) continue;

                if (!setReceivedEffectiveValue(configuration, element)) {
                    ConfigApi.LOGGER.warn("Config sync packet contains invalid value '{}' for config '{}'. The default config value will be used instead.", element, configuration.info().getId());
                }
            }
        }
    }

    private static  <T> boolean setReceivedEffectiveValue(AbstractConfiguration<T> config, NbtElement jsonElement) {
        DataResult<Pair<T, NbtElement>> result = config.getValueCodec().decode(NbtOps.INSTANCE, jsonElement);
        if (result.isError()) {
            return false;
        }

        ConfigManager.setEffectiveValue(config, result.getOrThrow().getFirst());
        return true;
    }

    public interface EntryFactory {
        AbstractConfigurationEntry<?, ?> build(ConfigOptionsScreen parent, ConfigsListWidget widget, AbstractConfiguration<?> configuration);
    }
}
