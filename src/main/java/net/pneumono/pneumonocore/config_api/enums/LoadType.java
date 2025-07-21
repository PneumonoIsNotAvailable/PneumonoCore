package net.pneumono.pneumonocore.config_api.enums;

import net.pneumono.pneumonocore.config_api.configurations.ConfigSettings;

/**
 * @see ConfigSettings#loadType(LoadType)
 */
public enum LoadType {
    INSTANT(0),
    RELOAD(1),
    RESTART(2);

    private final int requiredLevel;

    LoadType(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getLevel() {
        return requiredLevel;
    }

    public boolean canLoad(LoadType other) {
        return this.getLevel() >= other.getLevel();
    }
}
