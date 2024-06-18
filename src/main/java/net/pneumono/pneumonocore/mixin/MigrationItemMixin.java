package net.pneumono.pneumonocore.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.migration.Migration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
@SuppressWarnings("unused")
public abstract class MigrationItemMixin {
    @Inject(method = "fromNbt", at = @At("HEAD"))
    private static void fromMigratedNbt(RegistryWrapper.WrapperLookup registryLookup, NbtElement element, CallbackInfoReturnable<ItemStack> cir) {
        if (element instanceof NbtCompound nbt && nbt.contains("id", NbtElement.STRING_TYPE)) {
            String oldId = nbt.getString("id");
            if (Migration.getItems().containsKey(oldId)) {
                String newId = Migration.getItems().get(oldId).get();
                nbt.putString("id", newId);
                PneumonoCore.LOGGER.info("Item Stack \"'{}'\" successfully migrated to \"'{}'\"", oldId, newId);
            }
        }
    }
}
