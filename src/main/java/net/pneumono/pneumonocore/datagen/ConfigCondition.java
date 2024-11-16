package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;
import net.pneumono.pneumonocore.datagen.enums.Operator;

@SuppressWarnings("unused")
public class ConfigCondition extends AbstractConfigCondition {
    private final Identifier configuration;
    private final Operator operator;
    private final Object value;

    public ConfigCondition(Identifier configuration, Operator operator, Object value) {
        super(ConditionType.CONFIG);
        this.configuration = configuration;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean conditionFulfilled() {
        AbstractConfiguration<?> config = Configs.getConfig(configuration.getNamespace(), configuration.getPath());
        if (config != null) {
            boolean fulfilled;
            if (operator == Operator.EQUAL) {
                if (value instanceof String comparedValue && config.getValue() instanceof String configValue) {
                    fulfilled = comparedValue.equals(configValue);
                } else {
                    fulfilled = value == config.getValue();
                }
            } else {
                if (value instanceof Number numericValue && config.getValue() instanceof Number numericConfigValue) {
                    double configValue = numericConfigValue.doubleValue();
                    double comparedValue = numericValue.doubleValue();
                    fulfilled = switch (operator) {
                        case LESS -> configValue < comparedValue;
                        case GREATER -> configValue > comparedValue;
                        case LESS_OR_EQUAL -> configValue <= comparedValue;
                        case GREATER_OR_EQUAL -> configValue >= comparedValue;
                        default -> throw new IllegalStateException("Unexpected value: " + operator);
                    };
                } else {
                    fulfilled = true;
                }
            }
            return fulfilled;
        }
        return true;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.getType().toString());
        object.addProperty("configuration", this.configuration.toString());
        object.addProperty("operator", this.operator.toString());
        object.addProperty("value", this.value.toString());

        return object;
    }
}
