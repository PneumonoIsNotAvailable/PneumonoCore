package net.pneumono.pneumonocore.config_api.configurations;

@SuppressWarnings("unused")
public class ConfigSettings {
    protected boolean clientSided = false;
    protected String category = "misc";

    public ConfigSettings clientSide(boolean value) {
        this.clientSided = value;
        return this;
    }

    public ConfigSettings clientSide() {
        return this.clientSide(true);
    }

    public ConfigSettings category(String category) {
        if (category == null) throw new IllegalArgumentException("Category cannot be null!");
        this.category = category;
        return this;
    }
}
