package net.pneumono.pneumonocore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.pneumonocore.enchantment.ModEnchantment;
import net.pneumono.pneumonocore.util.PneumonoEnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"))
    private static boolean isModdedAcceptable(EnchantmentTarget instance, Item item, Operation<Boolean> original, @Local(ordinal = 0) Enchantment enchantment) {
        if (enchantment instanceof ModEnchantment || item instanceof EnchantableItem) {
            return PneumonoEnchantmentHelper.isAcceptable(enchantment, item);
        } else {
            return original.call(instance, item);
        }
    }
}
