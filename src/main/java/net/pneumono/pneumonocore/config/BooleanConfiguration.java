package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;

import java.util.Objects;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    /**
     * Creates a new boolean configuration. Register using {@link Configs#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue) {
        super(modID, name, environment, defaultValue);
    }

    private BooleanConfiguration(String modID, String name, ConfigEnv environment, boolean defaultValue, boolean loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
    }

    @Override
    public BooleanConfiguration fromElement(NbtElement element) {
        boolean b;
        String elementString = element.asString();
        if (Objects.equals(elementString, "true")) {
            b = true;
        } else if (Objects.equals(elementString, "false")) {
            b = false;
        } else {
            Configs.LOGGER.warn("Received server config value {} for config {} that was not a boolean! Using default value instead.", element, getID().toString());
            b = getDefaultValue();
        }
        return new BooleanConfiguration(modID, name, environment, getDefaultValue(), b);
    }

    @Override
    public BooleanConfiguration fromElement(JsonElement element) {
        boolean b;
        try {
            String elementString = element.getAsString();
            if (Objects.equals(elementString, "true")) {
                b = true;
            } else if (Objects.equals(elementString, "false")) {
                b = false;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (UnsupportedOperationException | IllegalStateException | IllegalArgumentException e) {
            Configs.LOGGER.warn("Config value {} for config {} was not a boolean! Using default value instead.", element, getID().toString());
            b = getDefaultValue();
        }
        return new BooleanConfiguration(modID, name, environment, getDefaultValue(), b);
    }

    @Override
    public String getClassID() {
        return "BooleanConfiguration";
    }
}
