package net.pneumono.pneumonocore.config;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pneumono.pneumonocore.PneumonoCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Configs {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    protected static List<ModConfigurations> CONFIGS = new ArrayList<>();

    /**
     * Registers a new configuration. Configuration values cannot be properly obtained via {@link AbstractConfiguration#getValue()} without first registering them.<p>
     * {@link #reload(String)} must be called in your ModInitializer because this system for configs is just terrible and I can't think of a better solution right now.
     *
     * @param configuration The configuration to be registered.
     * @return The registered configuration.
     */
    public static <T extends AbstractConfiguration<?>> T register(T configuration) {
        if (!isValid(configuration)) {
            LOGGER.error("Configuration " + configuration.modID + ":" + configuration.name + " was not registered successfully!");
            return configuration;
        }

        boolean modConfigExists = false;
        for (ModConfigurations modConfigs : CONFIGS) {
            if (Objects.equals(modConfigs.modID, configuration.getModID())) {
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
                    LOGGER.error("Configuration " + configuration.modID + ":" + configuration.name + " is a duplicate, and so was not registered!");
                }
                break;
            }
        }

        if (!modConfigExists) {
            CONFIGS.add(new ModConfigurations(configuration.getModID(), configuration));
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
        for (ModConfigurations modConfigs : CONFIGS) {
            if (Objects.equals(modConfigs.modID, modID)) {
                modConfigs.reload();
                break;
            }
        }
    }

    /**
     * Sends config sync packets to the specified clients. Should only be called on the logical server.
     *
     * @param players The players the packets will be sent to.
     */
    public static void sendS2CConfigSyncPacket(List<ServerPlayerEntity> players) {
        PacketByteBuf buffer = PacketByteBufs.create();
        String json = new PackagedConfigs().toJsonString();
        buffer.writeString(json);
        for (ServerPlayerEntity player : players) {
            ServerPlayNetworking.send(player, PneumonoCore.CONFIG_SYNC_ID, buffer);
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
        for (ModConfigurations modConfigs : CONFIGS) {
            if (modConfigs.modID.equals(modID) && modConfigs.configurations.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the condition with that name under that mod ID, or null if such a condition does not exist.
     */
    @SuppressWarnings("unused")
    public static AbstractConfiguration<?> getConfig(String modID, String name) {
        for (ModConfigurations modConfigs : CONFIGS) {
            if (modConfigs.modID.equals(modID)) {
                for (AbstractConfiguration<?> config : modConfigs.configurations) {
                    if (Objects.equals(config.name, name)) {
                        return config;
                    }
                }
            }
        }
        LOGGER.warn("Requested config " + modID + ":" + name + ", which does not exist!");
        return null;
    }
}
