package net.pneumono.pneumonocore.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.pneumonocore.enchantment.ModEnchantment;

public class PneumonoEnchantmentHelper {
    public static boolean isAcceptable(Enchantment enchantment, Item item) {
        return isAcceptable(enchantment, item.getDefaultStack());
    }

    public static boolean isAcceptable(Enchantment enchantment, ItemStack item) {
        if (enchantment instanceof ModEnchantment modEnchantment && item.getItem() instanceof EnchantableItem erisItem) {
            return modEnchantment.isAcceptableItem(item) || erisItem.isAcceptableEnchantment(enchantment);

        } else if (enchantment instanceof ModEnchantment modEnchantment) {
            return modEnchantment.isAcceptableItem(item);

        } else if (item.getItem() instanceof EnchantableItem enchantableItem) {
            return enchantableItem.isAcceptableEnchantment(enchantment);

        } else {
            return enchantment.isAcceptableItem(item);
        }
    }

    public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
        return EnchantmentHelper.getLevel(enchantment, stack) > 0;
    }
}
