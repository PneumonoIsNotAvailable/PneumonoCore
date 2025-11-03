package net.pneumono.pneumonocore.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;

//? if >=1.21.5 {
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
//?}

//? if >=1.20.2 {
import net.minecraft.advancements.AdvancementHolder;
//?}

@SuppressWarnings("unused")
public final class DatagenUtils {
    /**
     * Returns an advancement with the specified ID, for use as a parent for other advancements.
     */
    public static /*? if >=1.20.2 {*/AdvancementHolder/*?} else {*//*Advancement*//*?}*/ createDummyAdvancement(ResourceLocation id) {
        return Advancement.Builder.advancement().build(id);
    }

    public static /*? if >=1.20.2 {*/Criterion<InventoryChangeTrigger.TriggerInstance>/*?} else {*//*InventoryChangeTrigger.TriggerInstance*//*?}*/ itemCriterion(Item... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    /**
     * Use {@code null} for registry lookup if <1.21.2
     */
    public static /*? if >=1.20.2 {*/Criterion<InventoryChangeTrigger.TriggerInstance>/*?} else {*//*InventoryChangeTrigger.TriggerInstance*//*?}*/ itemTagCriterion(HolderGetter<Item> lookup, TagKey<Item> tag) {
        //? if >=1.21.2 {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(lookup, tag));
        //?} else {
        /*return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag).build());
        *///?}
    }

    public static /*? if >=1.20.2 {*/Criterion<InventoryChangeTrigger.TriggerInstance>/*?} else {*//*InventoryChangeTrigger.TriggerInstance*//*?}*/ countCriterion(MinMaxBounds.Ints range) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().withCount(range).build());
    }

    //? if >=1.21 {
    @SafeVarargs
    public static Criterion<InventoryChangeTrigger.TriggerInstance> enchantmentCriterion(HolderGetter<Enchantment> lookup, ResourceKey<Enchantment>... enchantments) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                //? if >=1.21.5 {
                ItemPredicate.Builder.item().withComponents(
                        DataComponentMatchers.Builder.components().partial(
                                DataComponentPredicates.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(
                                        Arrays.stream(enchantments).map(
                                                enchantment -> new EnchantmentPredicate(lookup.getOrThrow(enchantment), MinMaxBounds.Ints.atLeast(1))
                                        ).toList()
                                )
                        ).build()
                )
                //?} else {
                /*ItemPredicate.Builder.item().withSubPredicate(
                        ItemSubPredicates.ENCHANTMENTS,
                        ItemEnchantmentsPredicate.enchantments(
                                Arrays.stream(enchantments).map(
                                        enchantment -> new EnchantmentPredicate(lookup.getOrThrow(enchantment), MinMaxBounds.Ints.atLeast(1))
                                ).toList()
                        )
                )
                *///?}
        );
    }
    //?} else if >=1.20.5 {
    /*public static Criterion<InventoryChangeTrigger.TriggerInstance> enchantmentCriterion(Enchantment... enchantments) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().withSubPredicate(
                        ItemSubPredicates.ENCHANTMENTS,
                        ItemEnchantmentsPredicate.enchantments(
                                Arrays.stream(enchantments).map(
                                        enchantment -> new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.atLeast(1))
                                ).toList()
                        )
                )
        );
    }
    *///?} else {
    /*public static /^? if >=1.20.2 {^/Criterion<InventoryChangeTrigger.TriggerInstance>/^?} else {^//^InventoryChangeTrigger.TriggerInstance^//^?}^/ enchantmentCriterion(Enchantment... enchantments) {
        ItemPredicate.Builder builder = ItemPredicate.Builder.item();

        for (Enchantment enchantment : enchantments) {
            builder.hasEnchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.atLeast(1)));
        }

        return InventoryChangeTrigger.TriggerInstance.hasItems(builder.build());
    }
    *///?}
}
