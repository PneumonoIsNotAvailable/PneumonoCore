package net.pneumono.pneumonocore;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

@SuppressWarnings("unused")
public class PneumonoCoreModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return getModConfigScreenFactory(PneumonoCore.MOD_ID);
    }

    public static ConfigScreenFactory<?> getModConfigScreenFactory(String modID) {
        return null;
    }
}
