package net.pneumono.pneumonocore.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class ModEnchantment extends Enchantment {
    protected ModEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        boolean acceptable = true;
        for (Enchantment enchantment : getMutuallyExclusiveEnchantments()) {
            if (enchantment == other) {
                acceptable = false;
                break;
            }
        }

        return super.canAccept(other) && acceptable;
    }

    public Enchantment[] getMutuallyExclusiveEnchantments() {
        return new Enchantment[0];
    };
}
