package net.pneumono.pneumonocore.config;

public enum LoadType {
    /**
     * For configs that can be instantly changed.
     */
    INSTANT(0),
    /**
     * For configs that require a resource reload to be changed.
     */
    RELOAD(1),
    /**
     * For configs that require a server restart to be changed. Identical to {@link LoadType#RESTART_EVERYTHING} in singleplayer.
     */
    RESTART_SERVER(2),
    /**
     * For configs that require a server and client restart to be changed. Identical to {@link LoadType#RESTART_SERVER} in singleplayer.
     */
    RESTART_EVERYTHING(3);

    private final int value;

    LoadType(int value) {
        this.value = value;
    }

    public boolean canReload(LoadType loadType) {
        return this.value <= loadType.value;
    }
}
