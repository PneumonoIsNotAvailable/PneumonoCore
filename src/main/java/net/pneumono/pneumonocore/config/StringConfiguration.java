package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;

@SuppressWarnings("unused")
public class StringConfiguration extends AbstractConfiguration<String> {
    /**
     * Creates a new string configuration. Register using {@link Configs#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    private StringConfiguration(String modID, String name, ConfigEnv environment, String defaultValue, String loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    public StringConfiguration fromElement(NbtElement element) {
        return new StringConfiguration(modID, name, environment, getDefaultValue(), element.asString().orElse(getDefaultValue()));
    }

    @Override
    protected StringConfiguration fromElement(JsonElement element) {
        String s;
        try {
            s = element.getAsString();
        } catch (UnsupportedOperationException | IllegalStateException e) {
            Configs.LOGGER.warn("Config value {} for config {} was not a string! Using default value instead.", element, getID().toString());
            s = getDefaultValue();
        }
        return new StringConfiguration(modID, name, environment, getDefaultValue(), s);
    }

    @Override
    public String getClassID() {
        return "StringConfiguration";
    }
}
