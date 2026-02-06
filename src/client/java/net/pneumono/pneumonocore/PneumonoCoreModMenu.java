package net.pneumono.pneumonocore;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.ConfigFile;
import net.pneumono.pneumonocore.config_api.screen.ClientConfigOptionsScreen;

import java.util.HashMap;
import java.util.Map;

public final class PneumonoCoreModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return getModConfigScreenFactory(PneumonoCore.MOD_ID);
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> factoryMap = new HashMap<>();
        for (String namespace : ConfigApi.getConfigFiles().stream().map(ConfigFile::getModId).toList()) {
            factoryMap.put(namespace, getModConfigScreenFactory(namespace));
        }
        return factoryMap;
    }

    /**
     * Returns a config screen factory for the mod with that {@code modId}.
     */
    public static ConfigScreenFactory<?> getModConfigScreenFactory(String modId) {
        return parent -> new ClientConfigOptionsScreen(parent, modId);
    }
}
