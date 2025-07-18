package net.pneumono.pneumonocore.config_api;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConfigApi {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    private static final Map<String, ConfigFile> CONFIG_FILES = new HashMap<>();

    /**
     * Registers a mod's configurations. Configuration values cannot be properly obtained via {@link AbstractConfiguration#getValue()} without first registering them.<p>
     * Do NOT call this method multiple times!!!
     *
     * @param modID The mod ID of the mod the configs are being registered for.
     * @param configurations The configurations to be registered.
     */
    @SafeVarargs
    @SuppressWarnings("unused")
    public static <T extends AbstractConfiguration<?>> void register(String modID, T... configurations) {
        for (T configuration : configurations) {
            register(configuration);
        }
        readFromFile(modID);
    }

    private static <T extends AbstractConfiguration<?>> void register(T configuration) {
        if (!isValid(configuration)) {
            LOGGER.error("Config '{}' used an invalid identifier, and so was not registered.", configuration.getID());
            return;
        }

        ConfigFile configFile = CONFIG_FILES.computeIfAbsent(configuration.getModID(), ConfigFile::new);

        if (configFile.hasConfiguration(configuration.getName())) {
            LOGGER.error("Config '{}' is a duplicate, and so was not registered.", configuration.getID());
            return;
        }

        ConfigManager.setRegistered(configuration);
        configFile.addConfiguration(configuration);
    }

    public static String toTranslationKey(AbstractConfiguration<?> configuration, String suffix) {
        return configuration.getID().toTranslationKey("configs", suffix);
    }

    public static String toTranslationKey(AbstractConfiguration<?> configuration) {
        return configuration.getID().toTranslationKey("configs");
    }

    private static boolean isValid(AbstractConfiguration<?> configuration) {
        return !Objects.equals(configuration.getName(), "") && !Objects.equals(configuration.getModID(), "");
    }

    /**
     * Reloads the specified config file. <p>
     * It is recommended to use {@link #sendConfigSyncPacket(ServerPlayerEntity...)} to send a config sync packet to the client to update their configs if called on the logical server.
     *
     * @param modID The mod ID of the config file to reload.
     */
    public static void readFromFile(String modID) {
        ConfigFile modConfigs = CONFIG_FILES.get(modID);
        if (modConfigs != null) {
            modConfigs.readFromFile();
        }
    }

    /**
     * Sends config sync packets to the specified clients. Should only be called on the logical server.
     *
     * @param players The players the packets will be sent to.
     */
    public static void sendConfigSyncPacket(List<ServerPlayerEntity> players) {
        LOGGER.info("Sending config sync packets...");
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, new ConfigSyncS2CPayload(CONFIG_FILES.values()));
        }
    }

    /**
     * Sends config sync packets to the specified clients. Should only be called on the logical server.
     *
     * @param players The players the packets will be sent to.
     */
    public static void sendConfigSyncPacket(ServerPlayerEntity... players) {
        sendConfigSyncPacket(List.of(players));
    }

    /**
     * Returns {@code true} if at least one configuration has been registered for that mod ID, and {@code false} if not.
     */
    public static boolean hasConfigs(String modID) {
        ConfigFile modConfigs = CONFIG_FILES.get(modID);
        if (modConfigs != null) {
            return !modConfigs.getConfigurations().isEmpty();
        }
        return false;
    }

    public static Collection<ConfigFile> getConfigFiles() {
        return CONFIG_FILES.values();
    }

    public static ConfigFile getConfigFile(String modId) {
        return CONFIG_FILES.get(modId);
    }

    /**
     * Returns the configuration with that name under that mod ID, or null if such a configuration does not exist.
     */
    public static AbstractConfiguration<?> getConfig(Identifier id) {
        return getConfig(id.getNamespace(), id.getPath());
    }

    /**
     * Returns the configuration with that name under that mod ID, or null if such a configuration does not exist.
     */
    public static AbstractConfiguration<?> getConfig(String modID, String name) {
        ConfigFile modConfigs = CONFIG_FILES.get(modID);
        if (modConfigs != null) {
            return modConfigs.getConfiguration(name);
        }
        return null;
    }
}
