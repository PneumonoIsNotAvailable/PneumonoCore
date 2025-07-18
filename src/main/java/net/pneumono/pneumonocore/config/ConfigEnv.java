package net.pneumono.pneumonocore.config;

/**
 * @deprecated Use {@link net.pneumono.pneumonocore.config_api.enums.ConfigEnv} instead.
 */
@Deprecated
public enum ConfigEnv {
    SERVER,
    CLIENT;

    public net.pneumono.pneumonocore.config_api.enums.ConfigEnv toNew() {
        return switch (this) {
            case SERVER -> net.pneumono.pneumonocore.config_api.enums.ConfigEnv.SERVER;
            case CLIENT -> net.pneumono.pneumonocore.config_api.enums.ConfigEnv.CLIENT;
        };
    }
}
