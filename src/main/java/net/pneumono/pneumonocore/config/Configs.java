package net.pneumono.pneumonocore.config;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Configs {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    protected static Map<String, ModConfigurations> CONFIGS = new HashMap<>();

    private static final Map<String, ConfigCategory[]> CATEGORIES = new HashMap<>();

    /**
     * Registers config categories for a specific mod ID.
     * Do NOT call this method multiple times!!!
     *
     * @param modID The mod ID of the mod the config categories are being registered for.
     * @param categories The config categories to be registered.
     */
    @SuppressWarnings("unused")
    public static void registerCategories(String modID, ConfigCategory... categories) {
        CATEGORIES.put(modID, categories);
    }

    /**
     * Registers all your configurations at once via {@link #register(AbstractConfiguration)}, and calls {@link #reload(String)} after.<p>
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
        reload(modID);
    }

    /**
     * Registers a new configuration. Configuration values cannot be properly obtained via {@link AbstractConfiguration#getValue()} without first registering them.<p>
     * It is recommended to use {@link #register(String, AbstractConfiguration[])} rather than individually registering configurations. If it is not used, {@link #reload(String)} must be called in your ModInitializer after the configs are registered.
     *
     * @param configuration The configuration to be registered.
     * @return The registered configuration.
     */
    public static <T extends AbstractConfiguration<?>> T register(T configuration) {
        if (!isValid(configuration)) {
            LOGGER.error("Configuration {}:{} was not registered successfully!", configuration.modID, configuration.name);
            return configuration;
        }

        boolean modConfigExists = false;
        ModConfigurations modConfigs = CONFIGS.get(configuration.modID);
        if (modConfigs != null) {
            boolean isDuplicate = false;
            for (AbstractConfiguration<?> checkedConfiguration : modConfigs.configurations) {
                if (Objects.equals(checkedConfiguration.name, configuration.name)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                modConfigs.configurations.add(configuration);
                modConfigExists = true;
            } else {
                LOGGER.error("Configuration {}:{} is a duplicate, and so was not registered!", configuration.modID, configuration.name);
            }
        }

        if (!modConfigExists) {
            CONFIGS.put(configuration.getModID(), new ModConfigurations(configuration.getModID(), configuration));
        }

        configuration.registered = true;
        return configuration;
    }

    /**
     * Reloads the specified config file. <p>
     * It is recommended to use {@link #sendS2CConfigSyncPacket(ServerPlayerEntity...)} to send a config sync packet to the client to update their configs if called on the logical server.
     *
     * @param modID The mod ID of the config file to reload.
     */
    public static void reload(String modID) {
        ModConfigurations modConfigs = CONFIGS.get(modID);
        if (modConfigs != null) {
            modConfigs.reload();
        }
    }

    /**
     * Sends config sync packets to the specified clients. Should only be called on the logical server.
     *
     * @param players The players the packets will be sent to.
     */
    public static void sendS2CConfigSyncPacket(List<ServerPlayerEntity> players) {
        NbtCompound compound = new PackagedConfigs().toNbt();
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, new ConfigPayload(compound));
        }
    }

    /**
     * Sends config sync packets to the specified clients. Should only be called on the logical server.
     *
     * @param players The players the packets will be sent to.
     */
    protected static void sendS2CConfigSyncPacket(ServerPlayerEntity... players) {
        sendS2CConfigSyncPacket(List.of(players));
    }

    private static boolean isValid(AbstractConfiguration<?> configuration) {
        return configuration.getName() != null && !Objects.equals(configuration.getName(), "") && configuration.getModID() != null && !Objects.equals(configuration.getModID(), "");
    }

    /**
     * Returns {@code true} if at least one configuration has been registered for that mod ID, and {@code false} if not.
     */
    @SuppressWarnings("unused")
    public static boolean hasConfigs(String modID) {
        ModConfigurations modConfigs = CONFIGS.get(modID);
        if (modConfigs != null) {
            return !modConfigs.configurations.isEmpty();
        }
        return false;
    }

    /**
     * Returns the configuration with that name under that mod ID, or null if such a configuration does not exist.
     */
    @SuppressWarnings("unused")
    public static AbstractConfiguration<?> getConfig(String modID, String name) {
        ModConfigurations modConfigs = CONFIGS.get(modID);
        if (modConfigs != null) {
            for (AbstractConfiguration<?> config : modConfigs.configurations) {
                if (Objects.equals(config.name, name)) {
                    return config;
                }
            }
        }
        LOGGER.warn("Requested config {}:{}, which does not exist!", modID, name);
        return null;
    }

    /**
     * Returns a list of all config categories registered for that mod ID.
     *
     * @param modID The mod ID of the mod to get the config categories of.
     * @return The config categories.
     */
    public static ConfigCategory[] getCategories(String modID) {
        ConfigCategory[] categories = CATEGORIES.get(modID);
        return categories != null ? categories : new ConfigCategory[0];
    }
}
