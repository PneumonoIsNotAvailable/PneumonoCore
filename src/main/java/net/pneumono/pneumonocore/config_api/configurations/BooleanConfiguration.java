package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    /**
     * Creates a new boolean configuration. Register using {@link ConfigApi#register(String, AbstractConfiguration[])}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param modID The mod ID of the mod registering the configuration.
     * @param name The name of the configuration.
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public BooleanConfiguration(String modID, String name, boolean defaultValue, ConfigSettings settings) {
        super(modID, name, defaultValue, settings);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.BOOL;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("boolean");
    }
}
