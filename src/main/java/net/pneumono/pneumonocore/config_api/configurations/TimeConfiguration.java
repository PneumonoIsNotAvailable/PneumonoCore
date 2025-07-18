package net.pneumono.pneumonocore.config_api.configurations;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class TimeConfiguration extends AbstractConfiguration<Long> {
    /**
     * Creates a new time configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Time configurations are used for configuring amounts of time, e.g. the time taken for an item to be used.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param clientSided Whether the configuration is client-side (e.g. visual settings) or server-side (e.g. gameplay features).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public TimeConfiguration(String modID, String name, boolean clientSided, Long defaultValue) {
        super(modID, name, clientSided, defaultValue);
    }

    protected TimeConfiguration(String modID, String name, boolean clientSided, Long defaultValue, Long loadedValue) {
        super(modID, name, clientSided, defaultValue, loadedValue);
    }

    @Override
    public TimeConfiguration fromElement(NbtElement element) {
        long l;
        try {
            l = Long.parseLong(element.asString().orElse(""));
        } catch (NumberFormatException e) {
            ConfigApi.LOGGER.warn("Received server config value {} for config {} that was not an integer! Using default value instead.", element, getID().toString());
            l = getDefaultValue();
        }
        return new TimeConfiguration(modID, name, clientSided, getDefaultValue(), l);
    }

    @Override
    public TimeConfiguration fromElement(JsonElement element) {
        long l;
        try {
            l = element.getAsLong();
        } catch (UnsupportedOperationException | NumberFormatException | IllegalStateException e) {
            ConfigApi.LOGGER.warn("Config value {} for config {} was not a long! Using default value instead.", element, getID().toString());
            l = getDefaultValue();
        }
        return new TimeConfiguration(modID, name, clientSided, getDefaultValue(), l);
    }

    @Override
    public String getClassID() {
        return "TimeConfiguration";
    }
}
