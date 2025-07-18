package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Codec<T> valueCodec;


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
    public EnumConfiguration(String modID, String name, boolean clientSided, T defaultValue, Codec<T> valueCodec) {
        super(modID, name, clientSided, defaultValue);
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
