package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

public class BooleanConfiguration extends AbstractConfiguration<Boolean> {
    /**
     * Creates a new configuration that stores a {@code boolean}.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    @SuppressWarnings("unused")
    public BooleanConfiguration(boolean defaultValue, ConfigSettings settings) {
        super(defaultValue, settings);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.either(
                Codec.BOOL, Codec.STRING.comapFlatMap(this::parseString, Object::toString)
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
    private DataResult<Boolean> parseString(String string) {
        if (string.equalsIgnoreCase("true")) {
            return DataResult.success(true);
        }
        if (string.equalsIgnoreCase("false")) {
            return DataResult.success(false);
        }
        return DataResult.error(() -> "'" + string + "' cannot be parsed. ");
    }

    @Override
    protected Identifier getConfigTypeId() {
        return PneumonoCore.location("boolean");
    }
}
