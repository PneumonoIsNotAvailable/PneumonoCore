package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class IntegerConfiguration extends AbstractConfiguration<Integer> {
    /**
     * Creates a new configuration that stores an {@code integer}.
     *
     * <p>{@link BoundedIntegerConfiguration} should be used instead of this where possible,
     * as the config screen will use a slider instead of a text box, which is more user-friendly.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    public IntegerConfiguration(Integer defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.INT;
    }

    @Override
    protected Identifier getConfigTypeId() {
        return PneumonoCore.location("integer");
    }
}
