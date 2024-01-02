package net.pneumono.pneumonocore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.pneumono.pneumonocore.enchantment.EnchantableItem;
import net.pneumono.pneumonocore.enchantment.ModEnchantment;
import net.pneumono.pneumonocore.util.PneumonoEnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
@SuppressWarnings("unused")
public abstract class EnchantmentAnvilMixin {
    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean isModdedAcceptable(Enchantment instance, ItemStack itemStack, Operation<Boolean> original) {
        if (instance instanceof ModEnchantment || itemStack.getItem() instanceof EnchantableItem) {
            return PneumonoEnchantmentHelper.isAcceptable(instance, itemStack);
        } else {
            return original.call(instance, itemStack);
        }
    }
}
