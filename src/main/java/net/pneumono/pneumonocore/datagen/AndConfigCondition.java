package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;

@SuppressWarnings("unused")
public class AndConfigCondition extends AbstractConfigCondition {
    private final AbstractConfigCondition[] conditions;

    public AndConfigCondition(AbstractConfigCondition... conditions) {
        super(ConditionType.AND);
        this.conditions = conditions;
    }

    @Override
    public boolean conditionFulfilled() {
        boolean fulfilled = true;
        for (AbstractConfigCondition condition : conditions) {
            if (!condition.conditionFulfilled()) {
                fulfilled = false;
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
