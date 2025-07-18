package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class FloatConfiguration extends AbstractConfiguration<Float> {
    /**
     * Creates a new float configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Float configurations have a minimum value of 0 and a maximum value of 1. Any values read from files or set as default values that fall outside this range will be set to the nearest valid value (0 or 1)
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public FloatConfiguration(String modID, String name, float defaultValue, ConfigSettings settings) {
        super(modID, name, MathHelper.clamp(defaultValue, 0F, 1F), settings);
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.FLOAT;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("float");
    }
}
