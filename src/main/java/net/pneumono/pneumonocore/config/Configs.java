package net.pneumono.pneumonocore.config;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.configuration.AbstractConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Configs {
    private static final Map<String, ConfigFile> CONFIGS = new HashMap<>();

    public static <T> AbstractConfiguration<T> register(Identifier id, AbstractConfiguration<T> configuration) {
        configuration.setId(id);
        ConfigFile configFile = CONFIGS.computeIfAbsent(id.getNamespace(), ConfigFile::new);
        configFile.register(id, configuration);
        return configuration;
    }

    public static void initializeConfigFile(String modId) {
        ConfigFile configFile = CONFIGS.computeIfAbsent(modId, ConfigFile::new);
        configFile.saveValues();
    }

    @Deprecated
    public static void registerCategories(String modID, ConfigCategory... categories) {

    }

    @Deprecated
    @SafeVarargs
    public static <T extends net.pneumono.pneumonocore.config.AbstractConfiguration<?>> void register(String modID, T... configurations) {

    }
}
