package net.pneumono.pneumonocore;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pneumono.pneumonocore.config_api.screen.ClientConfigOptionsScreen;

public final class PneumonoCoreModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return getModConfigScreenFactory(PneumonoCore.MOD_ID);
    }

    /**
     * Returns a config screen factory for the mod with that {@code modId}.
     */
    public static ConfigScreenFactory<?> getModConfigScreenFactory(String modId) {
        return parent -> new ClientConfigOptionsScreen(parent, modId);
    }
}
