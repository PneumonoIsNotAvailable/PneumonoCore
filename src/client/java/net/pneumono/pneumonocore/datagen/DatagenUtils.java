package net.pneumono.pneumonocore.datagen;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.component.ComponentPredicateTypes;
import net.minecraft.predicate.component.ComponentsPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Arrays;

@SuppressWarnings("unused")
public class DatagenUtils {
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
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(lookup, tag));
    }

    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> countCriterion(NumberRange.IntRange range) {
        return InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().count(range));
    }

    @SafeVarargs
    public static AdvancementCriterion<InventoryChangedCriterion.Conditions> enchantmentCriterion(RegistryEntryLookup<Enchantment> lookup, RegistryKey<Enchantment>... enchantments) {
        return InventoryChangedCriterion.Conditions.items(
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
    }
}
