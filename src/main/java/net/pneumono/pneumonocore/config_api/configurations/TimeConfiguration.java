package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class TimeConfiguration extends AbstractConfiguration<Long> {
    /**
     * Creates a new time configuration. Register using {@link ConfigApi#register}.<p>
     * Time configurations are used for configuring amounts of time, e.g. the time taken for an item to be used.<p>
     * Configuration names use the translation key {@code "configs.<modID>.<name>"} in config menus.
     *
     * @param defaultValue The default value of the configuration.
     */
    @SuppressWarnings("unused")
    public TimeConfiguration(long defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Long> getValueCodec() {
        return Codec.LONG;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("time");
    }
}
