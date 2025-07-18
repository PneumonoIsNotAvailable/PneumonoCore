package net.pneumono.pneumonocore.config;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ModConfigurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.ConfigApi} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class Configs {
    public static final Logger LOGGER = LoggerFactory.getLogger("PneumonoCoreConfig");

    public static void registerCategories(String modID, ConfigCategory... categories) {
        ConfigApi.registerCategories(modID, Arrays.stream(categories)
                .map(ConfigCategory::toNew)
                .toArray(net.pneumono.pneumonocore.config_api.ConfigCategory[]::new)
        );
    }

    @SafeVarargs
    public static <T extends AbstractConfiguration<?, ?>> void register(String modID, T... configurations) {
        for (T configuration : configurations) {
            ConfigApi.register(modID, configuration.getWrappedConfiguration());
        }
        ConfigApi.reload(modID);
    }

    public static void reload(String modID) {
        ConfigApi.reload(modID);
    }

    public static void sendS2CConfigSyncPacket(List<ServerPlayerEntity> players) {
        ConfigApi.sendS2CConfigSyncPacket(players);
    }

    public static boolean hasConfigs(String modID) {
        return ConfigApi.hasConfigs(modID);
    }

    public static AbstractConfiguration<?, ?> getConfig(Identifier id) {
        return new WrappedConfiguration<>(ConfigApi.getConfig(id));
    }

    public static AbstractConfiguration<?, ?> getConfig(String modID, String name) {
        ModConfigurations modConfigs = ConfigApi.getModConfigs(modID);
        if (modConfigs != null) {
            for (net.pneumono.pneumonocore.config_api.AbstractConfiguration<?> config : modConfigs.configurations) {
                if (Objects.equals(config.getName(), name)) {
                    return new WrappedConfiguration<>(config);
                }
            }
        }
        LOGGER.warn("Requested config {}:{}, which does not exist!", modID, name);
        return null;
    }

    public static ConfigCategory[] getCategories(String modID) {
        net.pneumono.pneumonocore.config_api.ConfigCategory[] categories = ConfigApi.getCategories(modID);
        return Arrays.stream(categories).map(ConfigCategory::new).toArray(ConfigCategory[]::new);
    }

    public static class WrappedConfiguration<T, C extends net.pneumono.pneumonocore.config_api.AbstractConfiguration<T>> extends AbstractConfiguration<T, C> {
        public WrappedConfiguration(C configuration) {
            super(configuration);
        }
    }
}
