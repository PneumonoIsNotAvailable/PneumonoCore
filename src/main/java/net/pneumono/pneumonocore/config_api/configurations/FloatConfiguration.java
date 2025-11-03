package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class FloatConfiguration extends AbstractConfiguration<Float> {
    /**
     * Creates a new configuration that stores a {@code float}.
     *
     * <p>{@link BoundedFloatConfiguration} should be used instead of this where possible,
     * as the config screen will use a slider instead of a text box, which is more user-friendly.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    public FloatConfiguration(Float defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.FLOAT;
    }

    @Override
    protected ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("float");
    }
}
