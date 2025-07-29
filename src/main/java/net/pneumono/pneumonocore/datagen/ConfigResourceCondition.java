package net.pneumono.pneumonocore.datagen;

//? if >=1.20.6 {
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public record ConfigResourceCondition(Identifier configuration, Operator operator, String value) implements ResourceCondition {
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("configuration").forGetter(ConfigResourceCondition::configuration),
            Operator.CODEC.fieldOf("operation").forGetter(ConfigResourceCondition::operator),
            Codec.STRING.fieldOf("value").forGetter(ConfigResourceCondition::value)
    ).apply(instance, ConfigResourceCondition::new));

    public ConfigResourceCondition(AbstractConfiguration<?> configuration, Operator operator, String value) {
        this(configuration.info().getId(), operator, value);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return ConfigApiRegistry.RESOURCE_CONDITION_CONFIGURATIONS;
    }

    @Override
    public boolean test(/*? if >=1.21.4 {*//*@Nullable RegistryOps.RegistryInfoGetter registryInfo*//*?} else {*/@Nullable RegistryWrapper.WrapperLookup wrapperLookup/*?}*/) {
        AbstractConfiguration<?> config = ConfigApi.getConfig(configuration);
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

        @SuppressWarnings("deprecation")
        public static final EnumCodec<Operator> CODEC = StringIdentifiable.createCodec(Operator::values);

        @Override
        public String asString() {
            return this.name();
        }
    }
}
//?}