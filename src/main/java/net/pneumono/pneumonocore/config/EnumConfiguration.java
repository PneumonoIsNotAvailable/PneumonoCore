package net.pneumono.pneumonocore.config;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Class<T> enumClass;

    /**
     * Creates a new enum configuration. Register using {@link Configs#register(AbstractConfiguration)}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Enum configuration values use the translation keys {@code "configs.<modID>.<configName>.<valueName>"}.
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
    public EnumConfiguration<T> fromElement(NbtElement element) {
        T t;
        try {
            t = getEnumFromString(element.asString().orElse(null));
        } catch (UnsupportedOperationException | IllegalStateException | EnumConstantNotPresentException e) {
            Configs.LOGGER.warn("Received server config value {} for config {} that was not a suitable enum value! Using default value instead.", element, getID().toString());
            t = getDefaultValue();
        }
        return new EnumConfiguration<>(modID, name, environment, getDefaultValue(), t);
    }

    @Override
    protected EnumConfiguration<T> fromElement(JsonElement element) {
        T t;
        try {
            t = getEnumFromString(element.getAsString());
        } catch (UnsupportedOperationException | IllegalStateException | EnumConstantNotPresentException e) {
            Configs.LOGGER.warn("Config value {} for config {} was not a suitable enum value! Using default value instead.", element, getID().toString());
            t = getDefaultValue();
        }
        return new EnumConfiguration<>(modID, name, environment, getDefaultValue(), t);
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
