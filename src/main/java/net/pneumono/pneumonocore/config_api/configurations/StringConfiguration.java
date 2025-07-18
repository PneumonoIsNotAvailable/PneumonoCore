package net.pneumono.pneumonocore.config_api.configurations;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class StringConfiguration extends AbstractConfiguration<String> {
    /**
     * Creates a new string configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param clientSided Whether the configuration is client-side (e.g. visual settings) or server-side (e.g. gameplay features).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String modID, String name, boolean clientSided, String defaultValue) {
        super(modID, name, clientSided, defaultValue);
    }

    private StringConfiguration(String modID, String name, boolean clientSided, String defaultValue, String loadedValue) {
        super(modID, name, clientSided, defaultValue, loadedValue);
    }

    @Override
    public StringConfiguration fromElement(NbtElement element) {
        return new StringConfiguration(modID, name, clientSided, getDefaultValue(), element.asString().orElse(getDefaultValue()));
    }

    @Override
    public StringConfiguration fromElement(JsonElement element) {
        String s;
        try {
            s = element.getAsString();
        } catch (UnsupportedOperationException | IllegalStateException e) {
            ConfigApi.LOGGER.warn("Config value {} for config {} was not a string! Using default value instead.", element, getID().toString());
            s = getDefaultValue();
        }
        return new StringConfiguration(modID, name, clientSided, getDefaultValue(), s);
    }

    @Override
    public String getClassID() {
        return "StringConfiguration";
    }
}
