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

//? if >=1.21.8 {
/*import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
*///?}

//? if >=1.20.4 {
import net.minecraft.advancement.AdvancementEntry;
//?} else {
/*import net.minecraft.advancement.criterion.CriterionConditions;
*///?}

import java.util.Arrays;

@SuppressWarnings("unused")
public final class DatagenUtils {
    //? if >=1.20.4 {
    /**
     * Returns an {@link AdvancementEntry} with the specified ID, for use as a parent for other advancements.
     */
    public static AdvancementEntry createDummyAdvancement(Identifier id) {
        return Advancement.Builder.create().build(id);
    }

    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> itemCriterion(Item... items) {
        return InventoryChangedCriterion.Conditions.items(items);
    }

    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> itemTagCriterion(RegistryEntryLookup<Item> lookup, TagKey<Item> tag) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create()/*? if >=1.21.4 {*/.tag(lookup, tag)/*?} else {*//*.tag(tag)*//*?}*/);
    }

    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> countCriterion(NumberRange.IntRange range) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().count(range));
    }
    //?} else {
    /*public static Advancement createDummyAdvancement(Identifier id) {
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
    *///?}

    //? if >=1.21.1 {
    @SafeVarargs
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantmentCriterion(RegistryEntryLookup<Enchantment> lookup, RegistryKey<Enchantment>... enchantments) {
        //? if >=1.21.8 {
        /*return InventoryChangedCriterion.Conditions.items(
                ItemPredicate.Builder.create().components(
                        ComponentsPredicate.Builder.create().partial(
                                ComponentPredicateTypes.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(
                                        Arrays.stream(enchantments).map(
                                                enchantment -> new EnchantmentPredicate(lookup.getOrThrow(enchantment), NumberRange.IntRange.atLeast(1))
                                        ).toList()
                                )
                        ).build()
                )
        );
        *///?} else {
        return InventoryChangedCriterion.Conditions.items(
                ItemPredicate.Builder.create().subPredicate(
                        ItemSubPredicateTypes.ENCHANTMENTS,
                        EnchantmentsPredicate.enchantments(
                                Arrays.stream(enchantments).map(
                                        enchantment -> new EnchantmentPredicate(lookup.getOrThrow(enchantment), NumberRange.IntRange.atLeast(1))
                                ).toList()
                        )
                )
        );
        //?}
    }
    //?} else if >=1.20.6 {
    /*public static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantmentCriterion(Enchantment... enchantments) {
        return InventoryChangedCriterion.Conditions.items(
                ItemPredicate.Builder.create().subPredicate(
                        ItemSubPredicateTypes.ENCHANTMENTS,
                        EnchantmentsPredicate.enchantments(
                                Arrays.stream(enchantments).map(
                                        enchantment -> new EnchantmentPredicate(enchantment, NumberRange.IntRange.atLeast(1))
                                ).toList()
                        )
                )
        );
    }
    *///?} else if >=1.20.4 {
    /*public static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantmentCriterion(Enchantment... enchantments) {
        ItemPredicate.Builder builder = ItemPredicate.Builder.create();

        for (Enchantment enchantment : enchantments) {
            builder.enchantment(new EnchantmentPredicate(enchantment, NumberRange.IntRange.atLeast(1)));
        }

        return InventoryChangedCriterion.Conditions.items(builder);
    }
    *///?} else {
    /*public static CriterionConditions enchantmentCriterion(Enchantment... enchantments) {
        ItemPredicate.Builder builder = ItemPredicate.Builder.create();

        for (Enchantment enchantment : enchantments) {
            builder.enchantment(new EnchantmentPredicate(enchantment, NumberRange.IntRange.atLeast(1)));
        }

        return InventoryChangedCriterion.Conditions.items(builder.build());
    }
    *///?}
}
