package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonObject;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;

@SuppressWarnings("unused")
public class NotConfigCondition extends AbstractConfigCondition {
    private final AbstractConfigCondition condition;

    public NotConfigCondition(AbstractConfigCondition condition) {
        super(ConditionType.NOT);
        this.condition = condition;
    }

    @Override
    public boolean conditionFulfilled() {
        return !condition.conditionFulfilled();
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.getType().toString());

        object.add("condition", condition.toJson());

        return object;
    }
}
