package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config_api.ConfigApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerPlayerConfiguration<T> extends AbstractConfiguration<PerPlayerConfiguration.Values<T>> {
    private final AbstractConfiguration<T> configuration;
    private final Codec<Values<T>> codec;

    /**
     * Creates a new configuration that stores a value for each player.
     *
     * <p>You're on your own for this one. God save you.
     *
     * <p>Must be registered using {@link ConfigApi#register}, and once all configs for a given Mod ID are registered,
     * {@link ConfigApi#finishRegistry} must be called.
     */
    public PerPlayerConfiguration(AbstractConfiguration<T> configuration) {
        super(new Values<>(configuration.info().getDefaultValue()), configuration.info().getSettings());
        this.configuration = configuration;
        this.codec = RecordCodecBuilder.create(builder -> builder.group(
                configuration.getValueCodec().fieldOf("default_value").forGetter(Values::getDefaultValue),
                Codec.unboundedMap(
                        UUIDUtil.CODEC, configuration.getValueCodec()
                ).fieldOf("values").forGetter(Values::getValues)
        ).apply(builder, Values::new));
    }

    @Override
    public Codec<Values<T>> getValueCodec() {
        return codec;
    }

    @Override
    public ResourceLocation getConfigTypeId() {
        return PneumonoCore.location("per_player");
    }

    public ResourceLocation getSubConfigTypeId() {
        return configuration.info().getConfigTypeId();
    }

    public T getValue(UUID uuid) {
        return getValue().get(uuid);
    }

    public T getValue(Player player) {
        return getValue().get(player.getUUID());
    }

    public T getPerPlayerDefaultValue() {
        return getValue().getDefaultValue();
    }

    public static class Values<T> {
        private final T defaultValue;
        private final Map<UUID, T> map;

        public Values(T defaultValue, Map<UUID, T> map) {
            this.defaultValue = defaultValue;
            this.map = map;
        }

        public Values(T defaultValue) {
            this(defaultValue, new HashMap<>());
        }

        public T getDefaultValue() {
            return defaultValue;
        }

        public Map<UUID, T> getValues() {
            return map;
        }

        public T get(UUID uuid) {
            return map.getOrDefault(uuid, defaultValue);
        }

        public void put(UUID uuid, T value) {
            map.put(uuid, value);
        }
    }
}
