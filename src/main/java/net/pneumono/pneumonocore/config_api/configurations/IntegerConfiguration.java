package net.pneumono.pneumonocore.config_api.configurations;

import com.google.gson.JsonElement;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.MathHelper;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    private final int minValue;
    private final int maxValue;

    /**
     * Creates a new integer configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Integer configurations have set minimum and maximum values. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param clientSided Whether the configuration is client-side (e.g. visual settings) or server-side (e.g. gameplay features).
     * @param minValue The minimum value the configuration can be set to.
     * @param maxValue The maximum value the configuration can be set to.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public IntegerConfiguration(String modID, String name, boolean clientSided, int minValue, int maxValue, Integer defaultValue) {
        super(modID, name, clientSided, MathHelper.clamp(defaultValue, minValue, maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private IntegerConfiguration(String modID, String name, boolean clientSided, int minValue, int maxValue, Integer defaultValue, Integer loadedValue) {
        super(modID, name, clientSided, MathHelper.clamp(defaultValue, minValue, maxValue), MathHelper.clamp(loadedValue, minValue, maxValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public IntegerConfiguration fromElement(NbtElement element) {
        int i;
        try {
            i = Integer.parseInt(element.asString().orElse(""));
        } catch (NumberFormatException e) {
            ConfigApi.LOGGER.warn("Received config value {} for config {} that was not an integer! Using default value instead.", element, getID().toString());
            i = getDefaultValue();
        }
        return new IntegerConfiguration(modID, name, clientSided, minValue, maxValue, getDefaultValue(), i);
    }

    @Override
    public IntegerConfiguration fromElement(JsonElement element) {
        int i;
        try {
            i = element.getAsInt();
        } catch (UnsupportedOperationException | NumberFormatException | IllegalStateException e) {
            ConfigApi.LOGGER.warn("Config value {} for config {} was not an integer! Using default value instead.", element, getID().toString());
            i = getDefaultValue();
        }
        return new IntegerConfiguration(modID, name, clientSided, minValue, maxValue, getDefaultValue(), i);
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public String getClassID() {
        return "IntegerConfiguration";
    }
}
