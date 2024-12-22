package net.pneumono.pneumonocore.config;

public record ConfigCategory(String modID, String translationKey, AbstractConfiguration<?>... configurations) {
}
