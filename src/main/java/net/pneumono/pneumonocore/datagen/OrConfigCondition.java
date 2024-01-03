package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;

public class OrConfigCondition extends AbstractConfigCondition {
    private final AbstractConfigCondition[] conditions;

    public OrConfigCondition(AbstractConfigCondition... conditions) {
        super(ConditionType.OR);
        this.conditions = conditions;
    }

    @Override
    public boolean conditionFulfilled() {
        boolean fulfilled = conditions.length == 0;
        for (AbstractConfigCondition condition : conditions) {
            if (condition.conditionFulfilled()) {
                fulfilled = true;
                break;
            }
        }
        return fulfilled;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.getType().toString());

        JsonArray array = new JsonArray();
        for (AbstractConfigCondition condition : conditions) {
            array.add(condition.toJson());
        }
        object.add("conditions", array);

        return object;
    }
}
