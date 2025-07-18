package net.pneumono.pneumonocore.config_api;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;

public record ConfigCategory(String modID, String name, Identifier... configurations) {
    @SuppressWarnings("unused")
    public ConfigCategory(String modID, String name, AbstractConfiguration<?>... configurations) {
        this(modID, name, configsToIds(configurations));
    }

    public static ConfigCategory getEmpty() {
        return new ConfigCategory(PneumonoCore.MOD_ID, "empty", new Identifier[0]);
    }

    private static Identifier[] configsToIds(AbstractConfiguration<?>[] configurations) {
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
