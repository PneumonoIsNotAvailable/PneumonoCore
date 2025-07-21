package net.pneumono.pneumonocore.config;

import net.pneumono.pneumonocore.config_api.configurations.ConfigSettings;

/**
 * @deprecated Use {@link ConfigSettings#clientSide ConfigSettings.clientSide} instead.
 */
@Deprecated
public enum ConfigEnv {
    SERVER,
    CLIENT;

    public ConfigSettings toSettings() {
        return new ConfigSettings().clientSide(this == CLIENT);
    }
}
