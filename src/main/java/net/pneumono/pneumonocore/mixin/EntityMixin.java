package net.pneumono.pneumonocore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.pneumono.pneumonocore.entitydata.EntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getModdedData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }

        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    @SuppressWarnings("unused")
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        if (persistentData != null) {
            nbt.put("pneumonocore.mod_data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    @SuppressWarnings("unused")
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("pneumonocore.mod_data", 10)) {
            persistentData = nbt.getCompound("pneumonocore.mod_data");
        }
    }
}
