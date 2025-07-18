package net.pneumono.pneumonocore.config_api.configurations;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Class<T> enumClass;

    /**
     * Creates a new enum configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Enum configuration values use the translation keys {@code "configs.<modID>.<configName>.<valueName>"}.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param clientSided Whether the configuration is client-side (e.g. visual settings) or server-side (e.g. gameplay features).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public EnumConfiguration(String modID, String name, boolean clientSided, T defaultValue) {
        super(modID, name, clientSided, defaultValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    private EnumConfiguration(String modID, String name, boolean clientSided, T defaultValue, T loadedValue) {
        super(modID, name, clientSided, defaultValue, loadedValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    @Override
    public EnumConfiguration<T> fromElement(NbtElement element) {
        T t;
        try {
            t = getEnumFromString(element.asString().orElse(null));
        } catch (UnsupportedOperationException | IllegalStateException | EnumConstantNotPresentException e) {
            ConfigApi.LOGGER.warn("Received server config value {} for config {} that was not a suitable enum value! Using default value instead.", element, getID().toString());
            t = getDefaultValue();
        }
        return new EnumConfiguration<>(modID, name, clientSided, getDefaultValue(), t);
    }

    @Override
    public EnumConfiguration<T> fromElement(JsonElement element) {
        T t;
        try {
            t = getEnumFromString(element.getAsString());
        } catch (UnsupportedOperationException | IllegalStateException | EnumConstantNotPresentException e) {
            ConfigApi.LOGGER.warn("Config value {} for config {} was not a suitable enum value! Using default value instead.", element, getID().toString());
            t = getDefaultValue();
        }
        return new EnumConfiguration<>(modID, name, clientSided, getDefaultValue(), t);
    }

    private T getEnumFromString(String string) {
        for (T type : enumClass.getEnumConstants()) {
            if (type.name().equalsIgnoreCase(string)) {
                return type;
            }
        }
        throw new EnumConstantNotPresentException(enumClass, string);
    }

    @Override
    public String getClassID() {
        return "EnumConfiguration";
    }
}
