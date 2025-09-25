package net.pneumono.pneumonocore.datagen;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.*;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

//? if >=1.20.2 {
import net.minecraft.advancement.AdvancementEntry;
//?} else {
/*import net.minecraft.advancement.criterion.CriterionConditions;
*///?}

@SuppressWarnings("unused")
public final class DatagenUtils {
    //? if >=1.20.2 {
    /**
     * Returns an {@link AdvancementEntry} with the specified ID, for use as a parent for other advancements.
     */
    public static AdvancementEntry createDummyAdvancement(Identifier id) {
        return Advancement.Builder.create().build(id);
    }
    //?} else {
    /*/^*
     * Returns an {@link Advancement} with the specified ID, for use as a parent for other advancements.
     ^/
    public static Advancement createDummyAdvancement(Identifier id) {
        return Advancement.Builder.create().build(id);
    }
    *///?}

    //? if >=1.20.2 {
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> itemCriterion(Item... items) {
        return InventoryChangedCriterion.Conditions.items(items);
    }
    //?} else {
    /*public static CriterionConditions itemCriterion(Item... items) {
        return InventoryChangedCriterion.Conditions.items(items);
    }
    *///?}

    //? if >=1.20.2 {
    /**
     * Use {@code null} for registry lookup if <1.21.3
     */
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> itemTagCriterion(RegistryEntryLookup<Item> lookup, TagKey<Item> tag) {
        //? if >=1.21.3 {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(lookup, tag));
        //?} else {
        /*return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag));
        *///?}
    }
    //?} else {
    /*/^*
     * Use {@code null} for registry lookup if <1.21.3
     ^/
    public static CriterionConditions itemTagCriterion(RegistryEntryLookup<Item> lookup, TagKey<Item> tag) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build());
    }
    *///?}

    //? if >=1.20.2 {
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> countCriterion(NumberRange.IntRange range) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().count(range));
    }
    //?} else {
    /*public static CriterionConditions countCriterion(NumberRange.IntRange range) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().count(range).build());
    }
    *///?}

    //? if >=1.20.2 {
    @SafeVarargs
    @Deprecated
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantmentCriterion(RegistryEntryLookup<Enchantment> lookup, RegistryKey<Enchantment>... enchantments) {
        return null;
    }
    //?}
}
