package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Class<T> enumClass;

    /**
     * Creates a new enum configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "<modID>.configs.<name>"} in config menus.<p>
     * Enum configuration values use the translation keys {@code "<modID>.configs.<configName>.<valueName>"}.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public EnumConfiguration(String modID, String name, ConfigEnv environment, T defaultValue) {
        super(modID, name, environment, defaultValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    private EnumConfiguration(String modID, String name, ConfigEnv environment, T defaultValue, T loadedValue) {
        super(modID, name, environment, defaultValue, loadedValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }

    @Override
    public EnumConfiguration<T> fromElement(JsonElement element) {
        return new EnumConfiguration<>(modID, name, environment, getDefaultValue(), valueFromElement(element));
    }

    @Override
    public T valueFromElement(JsonElement element) {
        return getEnumFromString(element.getAsString());
    }

    private T getEnumFromString(String string) {
        for (T type : enumClass.getEnumConstants()) {
            if (type.name().equalsIgnoreCase(string)) {
                return type;
            }
        }
        return getDefaultValue();
    }

    public Class<T> getEnumClass() {
        return enumClass;
    }

    /**
     * Creates a new enum configuration. Replaced by version that doesn't specify a tooltip translation key, since the same format is always used and there isn't any point.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param environment Whether the configuration is server-side (e.g. gameplay features) or client-side (e.g. visual settings).
     * @param defaultValue The default value of the configuration.
     * @param tooltip Does nothing. Exists for backwards compatibility.
     */
    @SuppressWarnings("unused")
    @Deprecated
    public EnumConfiguration(String modID, String name, ConfigEnv environment, T defaultValue, String tooltip) {
        super(modID, name, environment, defaultValue);
        this.enumClass = defaultValue.getDeclaringClass();
    }
}
