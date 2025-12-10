package net.pneumono.pneumonocore.config_api.configurations;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for accessing protected methods in {@link AbstractConfiguration}
 *
 * <p>It's very unlikely anything other than the Config API itself should be using this!
 */
public class ConfigManager {
    public static <T, C extends AbstractConfiguration<T>> void setValue(C configuration, T value, LoadType loadType, @Nullable MinecraftServer server) {
        LoadType requiredType = configuration.info().getLoadType();
        if (loadType.canLoad(requiredType)) {
            setSavedValue(configuration, value);
            setEffectiveValue(configuration, value);
            if (server != null) {
                ConfigApi.sendConfigSyncPacket(PlayerLookup.all(server));
            }
        }
    }

    public static <T, C extends AbstractConfiguration<T>> void setSavedValue(C configuration, T value) {
        configuration.getSavedValue().set(value);
    }

    public static <T, C extends AbstractConfiguration<T>> void setEffectiveValue(C configuration, T value) {
        configuration.getEffectiveValue().set(value);
    }

    public static <T, C extends AbstractConfiguration<T>> T getSavedValue(C configuration) {
        return configuration.getSavedValue().get();
    }

    public static void register(AbstractConfiguration<?> configuration, Identifier id) {
        configuration.register(id);
    }

    @Deprecated
    public static void setCategory(AbstractConfiguration<?> configuration, String category) {
        configuration.setCategory(category);
    }
}
