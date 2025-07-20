package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config_api.configurations.ConfigSettings;

@Deprecated
public enum ConfigEnv {
    SERVER,
    CLIENT;

    public ConfigSettings toSettings() {
        return new ConfigSettings().clientSide(this == CLIENT);
    }
}
