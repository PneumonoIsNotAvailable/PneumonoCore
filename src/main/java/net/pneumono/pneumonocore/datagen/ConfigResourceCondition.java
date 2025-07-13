package net.pneumono.pneumonocore.datagen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import org.jetbrains.annotations.Nullable;

@Deprecated
public record ConfigResourceCondition(Identifier configuration, Operator operator, String value) implements ResourceCondition {
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("configuration").forGetter(ConfigResourceCondition::configuration),
            Operator.CODEC.fieldOf("operation").forGetter(ConfigResourceCondition::operator),
            Codec.STRING.fieldOf("value").forGetter(ConfigResourceCondition::value)
    ).apply(instance, ConfigResourceCondition::new));

    public ConfigResourceCondition(AbstractConfiguration<?> configuration, Operator operator, String value) {
        this(Identifier.of("null"), operator, value);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return PneumonoCore.RESOURCE_CONDITION_CONFIGURATIONS;
    }

    @Override
    public boolean test(@Nullable RegistryOps.RegistryInfoGetter registryInfo) {
        return false;
    }

    @Deprecated
    public enum Operator implements StringIdentifiable {
        EQUAL,
        LESS,
        GREATER,
        LESS_OR_EQUAL,
        GREATER_OR_EQUAL;

        // It's fineeeeee it's fine if things are deprecated it's fineeee this won't cause problems for future me at all
        public static final EnumCodec<Operator> CODEC = StringIdentifiable.createCodec(Operator::values);

        @Override
        public String asString() {
            return this.name();
        }
    }
}
