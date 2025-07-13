package net.pneumono.pneumonocore.config;

@Deprecated
public class TimeConfiguration extends AbstractConfiguration<Long> {
    public TimeConfiguration(String modID, String name, ConfigEnv environment, Long defaultValue) {
        super(modID, name, environment, defaultValue);
    }
}
