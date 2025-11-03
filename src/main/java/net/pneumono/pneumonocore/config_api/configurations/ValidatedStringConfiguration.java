package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

import java.util.function.Function;
import java.util.function.Predicate;

public class ValidatedStringConfiguration extends AbstractConfiguration<String> {
    private final Predicate<String> predicate;

    /**
     * Creates a new configuration that stores a {@code String} that must pass a predicate.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    public ValidatedStringConfiguration(String defaultValue, Predicate<String> predicate, ConfigSettings settings) {
        super(validateDefaultValue(defaultValue, predicate), settings);
        this.predicate = predicate;
    }

    private static String validateDefaultValue(String defaultValue, Predicate<String> predicate) {
        if (!predicate.test(defaultValue)) throw new IllegalArgumentException("Validated String Configurations cannot have a default value that does not pass its predicate!");
        return defaultValue;
    }

    public boolean test(String string) {
        return this.predicate.test(string);
    }

    @Override
    public Codec<String> getValueCodec() {
        Function<String, String> function = string -> test(string) ? string : this.info().getDefaultValue();
        return Codec.STRING.xmap(function, function);
    }

    @Override
    protected ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("validated_string");
    }
}
