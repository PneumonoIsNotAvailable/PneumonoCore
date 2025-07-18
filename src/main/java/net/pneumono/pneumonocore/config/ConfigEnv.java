package net.pneumono.pneumonocore.config;

@Deprecated
public enum ConfigEnv {
    SERVER,
    CLIENT;

    public boolean toNew() {
        return this == CLIENT;
    }
}
