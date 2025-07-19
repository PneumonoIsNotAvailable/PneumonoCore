package net.pneumono.pneumonocore.config_api.configurations;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.enums.LoadType;
import org.jetbrains.annotations.Nullable;

public class ConfigManager {
    public static <T, C extends AbstractConfiguration<T>> void setValue(C configuration, T value, LoadType loadType, @Nullable MinecraftServer server) {
        LoadType requiredType = configuration.getInfo().getLoadType();
        if (requiredType.canLoad(loadType)) {
            setLoadedValue(configuration, value);
            setSyncedValue(configuration, value);
            if (server != null) {
                ConfigApi.sendConfigSyncPacket(PlayerLookup.all(server));
            }
        }
    }

    public static <T, C extends AbstractConfiguration<T>> void setLoadedValue(C configuration, T value) {
        configuration.setLoadedValue(value);
    }

    public static <T, C extends AbstractConfiguration<T>> void setSyncedValue(C configuration, T value) {
        configuration.setSyncedValue(value);
    }

    public static <T, C extends AbstractConfiguration<T>> T getLoadedValue(C configuration) {
        return configuration.getLoadedValue();
    }

    public static void setRegistered(AbstractConfiguration<?> configuration, Identifier id) {
        configuration.setRegistered(id);
    }

    @Deprecated
    public static void setCategory(AbstractConfiguration<?> configuration, String category) {
        configuration.setCategory(category);
    }
}
