package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, Boolean defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    public BooleanConfiguration(String modID, String name, ConfigEnv environment, Boolean defaultValue, Boolean loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    protected BooleanConfiguration fromElement(JsonElement element) {
        return new BooleanConfiguration(modID, name, environment, getDefaultValue(), element.getAsBoolean());
    }
}
