package net.pneumono.pneumonocore.config_api;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Utility class for everything config-related.
 *
 * <p>Once configurations are registered, {@link #finishRegistry} should be called.
 */
public class ConfigApi {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    private static final Map<String, ConfigFile> CONFIG_FILES = new HashMap<>();

    /**
     * Registers a new configuration with the specified ID.
     *
     * <p>Configurations cannot be used until they are registered.
     *
     * <p>After all configurations are registered, {@link #finishRegistry} must be called.
     */
    public static <T extends AbstractConfiguration<?>> T register(Identifier id, T configuration) {
        if (id == null || Objects.equals(id.getNamespace(), "") || Objects.equals(id.getPath(), "")) {
            LOGGER.error("Config '{}' used an invalid identifier, and so was not registered.", id);
            return configuration;
        }

        ConfigFile configFile = CONFIG_FILES.computeIfAbsent(id.getNamespace(), ConfigFile::new);

        if (configFile.getConfiguration(id.getPath()) != null) {
            LOGGER.error("Config '{}' is a duplicate, and so was not registered.", id);
            return configuration;
        }

        ConfigManager.register(configuration, id);
        configFile.addConfiguration(configuration);

        return configuration;
    }

    public static void finishRegistry(String modId) {
        reloadValuesFromFile(modId, LoadType.RESTART);
    }

    public static void reloadValuesFromFiles(LoadType loadType) {
        for (ConfigFile configFile : CONFIG_FILES.values()) {
            configFile.readSavedFromFile(loadType);
        }
    }

    /**
     * Reloads the specified config file.
     *
     * <p>Configuration values will only be updated when a sufficiently high load type is used
     * (e.g. a RESTART configuration won't update its value if the specified load type is RELOAD).
     *
     * <p>{@link #sendConfigSyncPacket} should be used to update client configs if called on the server.
     */
    public static void reloadValuesFromFile(String modID, LoadType loadType) {
        ConfigFile configFile = CONFIG_FILES.get(modID);
        if (configFile != null) {
            configFile.readSavedFromFile(loadType);
        }
    }

    /**
     * Sends config sync packets to the specified players.
     */
    public static void sendConfigSyncPacket(Collection<ServerPlayerEntity> players) {
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, new ConfigSyncS2CPayload(CONFIG_FILES.values()));
        }
        LOGGER.info("Sent config sync packet to {} player(s)", players.size());
    }

    public static Collection<ConfigFile> getConfigFiles() {
        return CONFIG_FILES.values();
    }

    public static ConfigFile getConfigFile(String modId) {
        return CONFIG_FILES.get(modId);
    }

    public static AbstractConfiguration<?> getConfig(Identifier id) {
        ConfigFile modConfigs = getConfigFile(id.getNamespace());
        if (modConfigs != null) {
            return modConfigs.getConfiguration(id.getPath());
        }
        return null;
    }

    public static String toTranslationKey(AbstractConfiguration<?> configuration, String suffix) {
        return configuration.info().getId().toTranslationKey("configs", suffix);
    }

    public static String toTranslationKey(AbstractConfiguration<?> configuration) {
        return configuration.info().getId().toTranslationKey("configs");
    }
}
