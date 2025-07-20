package net.pneumono.pneumonocore.config_api.enums;

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
