package net.pneumono.pneumonocore.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @deprecated Use {@link ConfigApi} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class Configs {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    @Deprecated
    public static void registerCategories(String modID, ConfigCategory... categories) {
        for (ConfigCategory category : categories) for (ResourceLocation id : category.configurations()) {
            net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<?> configuration = ConfigApi.getConfig(id);
            if (configuration != null) {
                ConfigManager.setCategory(configuration, category.name());
            }
        }
    }

    /**
     * @deprecated Use {@link ConfigApi#register} followed by {@link ConfigApi#finishRegistry} instead.
     */
    @Deprecated
    @SafeVarargs
    public static <T extends AbstractConfiguration<?, ?>> void register(String modID, T... configurations) {
        for (T configuration : configurations) {
            ConfigApi.register(configuration.getID(), configuration.getWrappedConfiguration());
        }
        ConfigApi.finishRegistry(modID);
    }

    /**
     * @deprecated Use {@link ConfigApi#reloadValuesFromFile} instead.
     */
    @Deprecated
    public static void reload(String modID) {
        ConfigApi.reloadValuesFromFile(modID, LoadType.RELOAD);
    }

    /**
     * @deprecated Use {@link ConfigApi#sendConfigSyncPacket} instead.
     */
    @Deprecated
    public static void sendS2CConfigSyncPacket(List<ServerPlayer> players) {
        ConfigApi.sendConfigSyncPacket(players);
    }

    @Deprecated
    public static boolean hasConfigs(String modID) {
        ConfigFile configFile = ConfigApi.getConfigFile(modID);
        return configFile != null && !configFile.getConfigurations().isEmpty();
    }

    @Deprecated
    public static AbstractConfiguration<?, ?> getConfig(ResourceLocation id) {
        return new WrappedConfiguration<>(ConfigApi.getConfig(id));
    }

    @Deprecated
    public static AbstractConfiguration<?, ?> getConfig(String modID, String name) {
        ConfigFile modConfigs = ConfigApi.getConfigFile(modID);
        if (modConfigs != null) {
            net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<?> config = modConfigs.getConfiguration(name);
            if (config != null) {
                return new WrappedConfiguration<>(config);
            }
        }
        LOGGER.warn("Requested config {}:{}, which does not exist!", modID, name);
        return null;
    }

    @Deprecated
    public static ConfigCategory[] getCategories(String modID) {
        ConfigFile configFile = ConfigApi.getConfigFile(modID);

        Map<String, List<net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<?>>> categories = new HashMap<>();
        for (net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<?> configuration : configFile.getConfigurations()) {
            String category = configuration.info().getCategory();
            if (!Objects.equals(category, "misc") && !categories.containsKey(category)) {
                categories.computeIfAbsent(category, string -> new ArrayList<>()).add(configuration);
            }
        }

        return categories.entrySet().stream()
                .map(entry -> new ConfigCategory(
                        configFile.getModId(),
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(config -> config.info().getId())
                                .toArray(ResourceLocation[]::new)
                ))
                .toArray(ConfigCategory[]::new);
    }

    public static class WrappedConfiguration<T, C extends net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration<T>> extends AbstractConfiguration<T, C> {
        public WrappedConfiguration(String modId, String name, C configuration) {
            super(modId, name, configuration);
        }

        public WrappedConfiguration(C configuration) {
            super(configuration.info().getModId(), configuration.info().getName(), configuration);
        }
    }
}
