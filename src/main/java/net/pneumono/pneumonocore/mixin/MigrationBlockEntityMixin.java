package net.pneumono.pneumonocore.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.migration.Migration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public abstract class MigrationBlockEntityMixin {

    @Inject(method = "createFromNbt", at = @At("HEAD"))
    private static void createMigratedFromNbt(BlockPos pos, BlockState state, NbtCompound nbt, CallbackInfoReturnable<BlockEntity> cir) {
        if (nbt.contains("id", NbtElement.STRING_TYPE)) {
            String oldId = nbt.getString("id");
            if (Migration.getBlockEntities().containsKey(oldId)) {
                String newId = Migration.getBlockEntities().get(oldId).get();
                nbt.putString("id", newId);
                PneumonoCore.LOGGER.info("Block Entity \"'{}'\" successfully migrated to \"'{}'\"", oldId, newId);
            }
        }
    }
}
