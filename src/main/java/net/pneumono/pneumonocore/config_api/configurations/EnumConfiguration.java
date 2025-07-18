package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Codec<T> valueCodec;


    /**
     * Creates a new enum configuration. Register using {@link ConfigApi#register}.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.<p>
     * Enum configuration values use the translation keys {@code "configs.<modID>.<configName>.<valueName>"}.
     *
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public EnumConfiguration(T defaultValue, Codec<T> valueCodec, ConfigSettings settings) {
        super(defaultValue, settings);
        this.valueCodec = valueCodec;
    }

    @Override
    public Codec<T> getValueCodec() {
        return this.valueCodec;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("enum");
    }
}
