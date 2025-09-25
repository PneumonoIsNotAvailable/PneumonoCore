package net.pneumono.pneumonocore.mixin.client;

import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.util.CustomArmPoses;
import net.pneumono.pneumonocore.util.ExpandedEntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=1.21.4 {
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.LivingEntity;

@Mixin(ArmedEntityRenderState.class)
//?} else {
/*import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.mob.MobEntity;

@Mixin(BipedEntityRenderer.class)
*///?}
public abstract class EntityRenderStateMixin implements ExpandedEntityRenderState {
    @Nullable
    @Unique
    private Identifier leftArmPose = null;
    @Nullable
    @Unique
    private Identifier rightArmPose = null;

    //? if >=1.21.4 {
    @Inject(
            method = "updateRenderState",
            at = @At("RETURN")
    )
    private static void updateExpandedRenderState(LivingEntity entity, ArmedEntityRenderState armedState, ItemModelManager itemModelManager, CallbackInfo ci) {
    //?} else {
    /*@Inject(
            method = "updateRenderState(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V",
            at = @At("RETURN")
    )
    private void updateExpandedRenderState(MobEntity entity, BipedEntityRenderState armedState, float tickDelta, CallbackInfo ci) {
    *///?}
        if (!(armedState instanceof ExpandedEntityRenderState state)) return;

        state.pneumonoCore$setLeftArm(null);
        state.pneumonoCore$setRightArm(null);

        boolean isLeftSet = true;
        boolean isRightSet = true;
        for (CustomArmPoses.Pose pose : CustomArmPoses.getPoses()) {
            if (pose.twoHanded() && pose.shouldPose(entity, armedState, false /*? if >=1.21.4 {*/, itemModelManager/*?}*/)) {
                state.pneumonoCore$setLeftArm(pose.id());
                state.pneumonoCore$setRightArm(pose.id());
                return;
            }

            if (isLeftSet && pose.shouldPose(entity, armedState, false /*? if >=1.21.4 {*/, itemModelManager/*?}*/)) {
                state.pneumonoCore$setLeftArm(pose.id());
                isLeftSet = false;
            }
            if (isRightSet && pose.shouldPose(entity, armedState, true /*? if >=1.21.4 {*/, itemModelManager/*?}*/)) {
                state.pneumonoCore$setRightArm(pose.id());
                isRightSet = false;
            }
        }
    }

    @Override
    public void pneumonoCore$setLeftArm(Identifier id) {
        this.leftArmPose = id;
    }

    @Override
    public void pneumonoCore$setRightArm(Identifier id) {
        this.rightArmPose = id;
    }

    @Nullable
    public Identifier pneumonoCore$getLeftArm() {
        return this.leftArmPose;
    }

    @Nullable
    public Identifier pneumonoCore$getRightArm() {
        return this.rightArmPose;
    }
}
