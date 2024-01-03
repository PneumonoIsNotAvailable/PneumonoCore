package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonObject;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;

public abstract class AbstractConfigCondition {
    private final ConditionType type;

    public AbstractConfigCondition(ConditionType type) {
        this.type = type;
    }

    public ConditionType getType() {
        return type;
    }

    public abstract boolean conditionFulfilled();

    public abstract JsonObject toJson();
}
