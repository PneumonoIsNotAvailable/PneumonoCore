package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class StringConfiguration extends AbstractConfiguration<String> {
    /**
     * Creates a new string configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public StringConfiguration(String defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<String> getValueCodec() {
        return Codec.STRING;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("string");
    }
}
