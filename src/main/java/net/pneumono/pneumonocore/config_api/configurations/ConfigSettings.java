package net.pneumono.pneumonocore.config_api.configurations;

import net.pneumono.pneumonocore.config_api.enums.LoadType;

@SuppressWarnings("unused")
public class ConfigSettings {
    protected boolean clientSided = false;
    protected String category = "misc";
    protected LoadType loadType = LoadType.RELOAD;

    public ConfigSettings clientSide(boolean value) {
        this.clientSided = value;
        return this;
    }

    public ConfigSettings clientSide() {
        return this.clientSide(true);
    }

    public ConfigSettings category(String category) {
        if (category == null) throw new IllegalArgumentException("Category cannot be null.");
        this.category = category;
        return this;
    }

    public ConfigSettings loadType(LoadType loadType) {
        if (loadType == null) throw new IllegalArgumentException("Load Type cannot be null.");
        this.loadType = loadType;
        return this;
    }
}
