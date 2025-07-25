package net.pneumono.pneumonocore.config;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.configurations.ConfigSettings#category(String) ConfigSettings.category} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public record ConfigCategory(String modID, String name, Identifier... configurations) {
    public ConfigCategory(String modID, String name, AbstractConfiguration<?, ?>... configurations) {
        this(modID, name, configsToIds(configurations));
    }

    public static ConfigCategory getEmpty() {
        return new ConfigCategory(PneumonoCore.MOD_ID, "empty", new Identifier[0]);
    }

    private static Identifier[] configsToIds(AbstractConfiguration<?, ?>[] configurations) {
        Identifier[] ids = new Identifier[configurations.length];
        for (int i = 0; i<configurations.length; ++i) {
            ids[i] = configurations[i].getID();
        }
        return ids;
    }

    public String getTranslationKey() {
        return "configs.category." + modID + "." + name;
    }
}
