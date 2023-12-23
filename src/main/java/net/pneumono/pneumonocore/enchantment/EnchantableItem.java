package net.pneumono.pneumonocore.enchantment;

import net.minecraft.enchantment.Enchantment;

public interface EnchantableItem {
    boolean isAcceptableEnchantment(Enchantment enchantment);
}