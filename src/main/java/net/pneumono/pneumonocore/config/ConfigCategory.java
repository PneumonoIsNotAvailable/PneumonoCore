package net.pneumono.pneumonocore.config;

import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.ConfigSettings#category(String) ConfigSettings.category} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public record ConfigCategory(String modID, String name, ResourceLocation... configurations) {
    public ConfigCategory(String modID, String name, AbstractConfiguration<?, ?>... configurations) {
        this(modID, name, configsToIds(configurations));
    }

    public static ConfigCategory getEmpty() {
        return new ConfigCategory(PneumonoCore.MOD_ID, "empty", new ResourceLocation[0]);
    }

    private static ResourceLocation[] configsToIds(AbstractConfiguration<?, ?>[] configurations) {
        ResourceLocation[] ids = new ResourceLocation[configurations.length];
        for (int i = 0; i<configurations.length; ++i) {
            ids[i] = configurations[i].getID();
        }
        return ids;
    }

    public String getTranslationKey() {
        return "configs.category." + modID + "." + name;
    }
}
