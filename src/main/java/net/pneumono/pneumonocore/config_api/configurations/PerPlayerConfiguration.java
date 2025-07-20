package net.pneumono.pneumonocore.config_api.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.pneumono.pneumonocore.PneumonoCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PerPlayerConfiguration<T> extends AbstractConfiguration<PerPlayerConfiguration.Values<T>> {
    private final AbstractConfiguration<T> configuration;
    private final Codec<Values<T>> codec;

    public PerPlayerConfiguration(AbstractConfiguration<T> configuration) {
        super(new Values<>(configuration.info().getDefaultValue()), configuration.info().getSettings());
        this.configuration = configuration;
        this.codec = RecordCodecBuilder.create(builder -> builder.group(
                configuration.getValueCodec().fieldOf("default_value").forGetter(Values::getDefaultValue),
                Codec.unboundedMap(
                        Uuids.INT_STREAM_CODEC, configuration.getValueCodec()
                ).fieldOf("values").forGetter(Values::getValues)
        ).apply(builder, Values::new));
    }

    @Override
    public Codec<Values<T>> getValueCodec() {
        return codec;
    }

    @Override
    public Identifier getConfigTypeId() {
        return PneumonoCore.identifier("per_player");
    }

    public Identifier getSubConfigTypeId() {
        return configuration.info().getConfigTypeId();
    }

    public T getValue(UUID uuid) {
        return getValue().get(uuid);
    }

    public T getValue(PlayerEntity player) {
        return getValue().get(player.getUuid());
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
