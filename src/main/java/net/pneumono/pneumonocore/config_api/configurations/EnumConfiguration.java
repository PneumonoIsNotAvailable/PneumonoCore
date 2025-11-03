package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.util.CodecUtil;

public class EnumConfiguration<T extends Enum<T>> extends AbstractConfiguration<T> {
    private final Codec<T> valueCodec;

    /**
     * Creates a new configuration that stores a value from an {@code Enum}.
     *
     * <p>Config enum values use the translation key {@code configs.<modId>.<configName>.<valueName>}.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    @SuppressWarnings("unused")
    public EnumConfiguration(T defaultValue, Codec<T> valueCodec, ConfigSettings settings) {
        super(defaultValue, settings);
        this.valueCodec = valueCodec;
    }

    public EnumConfiguration(T defaultValue, ConfigSettings settings) {
        this(defaultValue, CodecUtil.createEnumCodec(defaultValue.getDeclaringClass().getEnumConstants()), settings);
    }

    @Override
    public Codec<T> getValueCodec() {
        return this.valueCodec;
    }

    @Override
    protected ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("enum");
    }
}
