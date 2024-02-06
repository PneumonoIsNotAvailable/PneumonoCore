package net.pneumono.pneumonocore.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.config.AbstractConfiguration;
import net.pneumono.pneumonocore.config.Configs;
import net.pneumono.pneumonocore.datagen.enums.ConditionType;
import net.pneumono.pneumonocore.datagen.enums.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PneumonoDatagenHelper {
    /**
     * Returns a {@link ConditionJsonProvider} for Fabric Resource Conditions.<p>
     * You can use combinations of {@link AndConfigCondition}, {@link OrConfigCondition}, {@link NotConfigCondition}, and {@link ConfigCondition} to make more detailed conditions, or simply use {@link ConfigCondition} instances alone.<p>
     * If multiple conditions are passed into this method, they must all be fulfilled to satisfy the Resource Condition (like {@link AndConfigCondition}).
     *
     * @param configs The config conditions
     * @return A JSON provider for use in Data Generation
     */
    @SuppressWarnings("unused")
    public static ConditionJsonProvider configValues(AbstractConfigCondition... configs) {
        return new ConditionJsonProvider() {
            @Override
            public Identifier getConditionId() {
                return new Identifier(PneumonoCore.MOD_ID, "config_values");
            }

            @Override
            public void writeParameters(JsonObject object) {
                JsonArray array = new JsonArray();
                for (AbstractConfigCondition condition : configs) {
                    array.add(condition.toJson());
                }
                object.add("conditions", array);
            }
        };
    }

    public static void registerResourceConditions() {
        ResourceConditions.register(new Identifier(PneumonoCore.MOD_ID, "config_values"), object -> {
            List<JsonElement> jsonConditions = JsonHelper.getArray(object, "conditions").asList();
            List<AbstractConfigCondition> conditions = new ArrayList<>();
            for (JsonElement jsonCondition : jsonConditions) {
                conditions.add(parseConditionJson(jsonCondition.getAsJsonObject()));
            }

            Supplier<Boolean> booleanSupplier = () -> conditionsFulfilled(conditions.toArray(new AbstractConfigCondition[0]));
            return booleanSupplier.get();
        });
    }

    public static boolean conditionsFulfilled(AbstractConfigCondition... conditions) {
        boolean fulfilled = true;
        for (AbstractConfigCondition condition : conditions) {
            if (!condition.conditionFulfilled()) {
                fulfilled = false;
                break;
            }
        }
        return fulfilled;
    }

    private static AbstractConfigCondition parseConditionJson(JsonObject object) {
        ConditionType type = ConditionType.fromString(JsonHelper.getString(object, "type"));
        switch (type) {
            case AND -> {
                List<JsonElement> jsonConditions = JsonHelper.getArray(object, "conditions").asList();
                List<AbstractConfigCondition> conditions = new ArrayList<>();
                for (JsonElement jsonCondition : jsonConditions) {
                    conditions.add(parseConditionJson(jsonCondition.getAsJsonObject()));
                }
                return new AndConfigCondition(conditions.toArray(new AbstractConfigCondition[0]));
            }
            case OR -> {
                List<JsonElement> jsonConditions = JsonHelper.getArray(object, "conditions").asList();
                List<AbstractConfigCondition> conditions = new ArrayList<>();
                for (JsonElement jsonCondition : jsonConditions) {
                    conditions.add(parseConditionJson(jsonCondition.getAsJsonObject()));
                }
                return new OrConfigCondition(conditions.toArray(new AbstractConfigCondition[0]));
            }
            case NOT -> {
                JsonElement jsonCondition = JsonHelper.getElement(object, "condition");
                return new NotConfigCondition(parseConditionJson(jsonCondition.getAsJsonObject()));
            }
            default -> {
                Identifier configurationID = new Identifier(JsonHelper.getString(object, "configuration"));
                AbstractConfiguration<?> configuration = Configs.getConfig(configurationID.getNamespace(), configurationID.getPath());
                if (configuration != null) {
                    Operator operator = Operator.fromString(JsonHelper.getString(object, "operator"));
                    return new ConfigCondition(configuration.getID(), operator, configuration.valueFromElement(JsonHelper.getElement(object, "value")));
                }
            }
        }
        PneumonoCore.LOGGER.warn("Invalid resource condition JSON!");
        return null;
    }

    @SuppressWarnings("unused")
    public static ItemPredicate getTagItemPredicate(TagKey<Item> tagKey) {
        return new ItemPredicate(tagKey, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
    }

    @SuppressWarnings("unused")
    public static ItemPredicate getEnchantmentItemPredicate(EnchantmentPredicate enchantment) {
        return new ItemPredicate(null, null, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new EnchantmentPredicate[]{enchantment}, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY);
    }

    /**
     * Returns a dummy advancement for use as a parent in other advancements.
     *
     * @param advancementID The ID of the advancement a dummy is needed for.
     * @return A dummy advancement.
     */
    @SuppressWarnings("unused")
    public static Advancement getDummyAdvancement(Identifier advancementID) {
        return Advancement.Builder.create()
                .display(
                        Items.BARRIER,
                        Text.literal("Uh oh!"),
                        Text.literal("If you're reading this, something has gone very, very wrong"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        false,
                        false,
                        false
                )
                .criterion("impossible", new ImpossibleCriterion.Conditions())
                .build(advancementID);
    }
}
