package net.pneumono.pneumonocore.config_api.configurations;

@SuppressWarnings("unused")
public class ConfigSettings {
    protected boolean clientSided = false;

    public ConfigSettings clientSide(boolean value) {
        this.clientSided = value;
        return this;
    }

    public ConfigSettings clientSide() {
        return this.clientSide(true);
    }
}
