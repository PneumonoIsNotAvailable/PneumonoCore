package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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
        return Codec.either(
                Codec.LONG, Codec.STRING.comapFlatMap(this::parseString, Object::toString)
        ).xmap(
                either -> {
                    if (either.left().isPresent()) {
                        return either.left().get();
                    } else if (either.right().isPresent()) {
                        return either.right().get();
                    }
                    return null;
                },
                Either::left
        );
    }

    // Exists for backwards compatibility - previous config system saved everything as strings
    private DataResult<Long> parseString(String string) {
        try {
            return DataResult.success(Long.parseLong(string));
        } catch (NumberFormatException e) {
            return DataResult.error(() -> "'" + string + "' cannot be parsed. " + e.getMessage());
        }
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("time");
    }
}
