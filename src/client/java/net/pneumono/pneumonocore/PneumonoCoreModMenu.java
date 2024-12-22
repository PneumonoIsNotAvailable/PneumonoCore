package net.pneumono.pneumonocore;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pneumono.pneumonocore.config.ConfigOptionsScreen;
import net.pneumono.pneumonocore.config.Configs;

@SuppressWarnings("unused")
public class PneumonoCoreModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return getModConfigScreenFactory(PneumonoCore.MOD_ID);
    }

    public static ConfigScreenFactory<?> getModConfigScreenFactory(String modID) {
        return Configs.hasConfigs(modID) ? parent -> new ConfigOptionsScreen(parent, modID) : null;
    }
}
