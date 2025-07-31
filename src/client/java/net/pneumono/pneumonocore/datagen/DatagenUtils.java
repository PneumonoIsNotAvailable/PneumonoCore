package net.pneumono.pneumonocore.datagen;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public final class DatagenUtils {
    /**
     * Returns an {@link Advancement} with the specified ID, for use as a parent for other advancements.
     */
    public static Advancement createDummyAdvancement(Identifier id) {
        return Advancement.Builder.create().build(id);
    }

    public static CriterionConditions itemCriterion(Item... items) {
        return InventoryChangedCriterion.Conditions.items(items);
    }

    public static CriterionConditions itemTagCriterion(TagKey<Item> tag) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build());
    }

    public static CriterionConditions countCriterion(NumberRange.IntRange range) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().count(range).build());
    }

    public static CriterionConditions enchantmentCriterion(RegistryEntryLookup<Enchantment> lookup, Enchantment... enchantments) {
        ItemPredicate.Builder builder = ItemPredicate.Builder.create();

        for (Enchantment enchantment : enchantments) {
            builder.enchantment(new EnchantmentPredicate(enchantment, NumberRange.IntRange.atLeast(1)));
        }

        return InventoryChangedCriterion.Conditions.items(builder.build());
    }
}
