package net.pneumono.pneumonocore.datagen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.configurations.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.ConfigApi;
import net.pneumono.pneumonocore.config_api.registry.ConfigApiRegistry;
import net.pneumono.pneumonocore.util.CodecUtil;
import org.jetbrains.annotations.Nullable;

//? if >=1.21.3 {
import net.minecraft.registry.RegistryOps;
//?} else {
/*import net.minecraft.registry.RegistryWrapper;
*///?}

//? if >=1.20.6 {
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
//?}

/**
 * Cannot be used <1.20.6, for obvious reasons
 */
@SuppressWarnings("unused")
public record ConfigResourceCondition(Identifier configuration, Operator operator, String value) /*? if >=1.20.6 {*/implements ResourceCondition/*?}*/ {
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("configuration").forGetter(ConfigResourceCondition::configuration),
            Operator.CODEC.fieldOf("operation").forGetter(ConfigResourceCondition::operator),
            Codec.STRING.fieldOf("value").forGetter(ConfigResourceCondition::value)
    ).apply(instance, ConfigResourceCondition::new));

    public ConfigResourceCondition(AbstractConfiguration<?> configuration, Operator operator, String value) {
        this(configuration.info().getId(), operator, value);
    }

    //? if >=1.20.6 {
    @Override
    public ResourceConditionType<?> getType() {
        return ConfigApiRegistry.RESOURCE_CONDITION_CONFIGURATIONS;
    }
    //?}

    //? if >=1.20.6 {
    @Override
    //?}
    public boolean test(@Nullable /*? if >=1.21.3 {*/RegistryOps.RegistryInfoGetter/*?} else {*//*RegistryWrapper.WrapperLookup*//*?}*/ registryInfo) {
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

    public enum Operator {
        EQUAL,
        LESS,
        GREATER,
        LESS_OR_EQUAL,
        GREATER_OR_EQUAL;

        public static final Codec<Operator> CODEC = CodecUtil.createEnumCodec(Operator.values());
    }
}
