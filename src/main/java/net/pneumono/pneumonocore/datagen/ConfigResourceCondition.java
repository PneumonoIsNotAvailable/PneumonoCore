package net.pneumono.pneumonocore.datagen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.Configs;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public record ConfigResourceCondition(Identifier configuration, Operator operator, String value) implements ResourceCondition {
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("configuration").forGetter(ConfigResourceCondition::configuration),
            Operator.CODEC.fieldOf("operation").forGetter(ConfigResourceCondition::operator),
            Codec.STRING.fieldOf("value").forGetter(ConfigResourceCondition::value)
    ).apply(instance, ConfigResourceCondition::new));

    public ConfigResourceCondition(AbstractConfiguration<?> configuration, Operator operator, String value) {
        this(configuration.getID(), operator, value);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return PneumonoCore.RESOURCE_CONDITION_CONFIGURATIONS;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup wrapperLookup) {
        AbstractConfiguration<?> config = Configs.getConfig(configuration);
        if (config == null) {
            return false;
        }
        if (operator == Operator.EQUAL) {
            return value.equals(config.getValue().toString());
        } else if (config.getValue() instanceof Number numericConfigValue) {
            double comparedValue;
            try {
                comparedValue = Double.parseDouble(value);
            } catch (NumberFormatException ignored) {
                return false;
            }

            double configValue = numericConfigValue.doubleValue();
            return switch (operator) {
                case LESS -> configValue < comparedValue;
                case GREATER -> configValue > comparedValue;
                case LESS_OR_EQUAL -> configValue <= comparedValue;
                case GREATER_OR_EQUAL -> configValue >= comparedValue;
                default -> false;
            };
        }
        return false;
    }

    public enum Operator implements StringIdentifiable {
        EQUAL,
        LESS,
        GREATER,
        LESS_OR_EQUAL,
        GREATER_OR_EQUAL;

        // It's fineeeeee it's fine if things are deprecated it's fineeee this won't cause problems for future me at all
        @SuppressWarnings("deprecation")
        public static final EnumCodec<Operator> CODEC = StringIdentifiable.createCodec(Operator::values);

        public static Operator fromString(String string) {
            return switch (string.toUpperCase()) {
                case "EQUAL" -> EQUAL;
                case "LESS" -> LESS;
                case "GREATER" -> GREATER;
                case "LESS_OR_EQUAL" -> LESS_OR_EQUAL;
                case "GREATER_OR_EQUAL" -> GREATER_OR_EQUAL;
                default -> null;
            };
        }

        @Override
        public String asString() {
            return this.name();
        }
    }
}
