package net.pneumono.pneumonocore.config;

import net.minecraft.util.Identifier;

@Deprecated
public record ConfigCategory(String modID, String name, Identifier... configurations) {
    public ConfigCategory(String modID, String name, AbstractConfiguration<?>... configurations) {
        this(modID, name, new Identifier[0]);
    }
}
