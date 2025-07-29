package net.pneumono.pneumonocore.mixin.client;

//? if >=1.21.4 {
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.pneumono.pneumonocore.util.CustomArmPoses;
import net.pneumono.pneumonocore.util.ExpandedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {
    @Inject(
            method = "positionLeftArm",
            at = @At("HEAD"),
            cancellable = true
    )
    public void applyCustomLeftArmPose(BipedEntityRenderState bipedEntityRenderState, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (!(bipedEntityRenderState instanceof ExpandedEntityRenderState state)) return;

        if (state.pneumonoCore$getLeftArm() == null) return;

        for (CustomArmPoses.Pose pose : CustomArmPoses.getPoses()) {
            if (state.pneumonoCore$getLeftArm().equals(pose.id())) {
                pose.leftArm().pose((BipedEntityModel<?>)(Object)this, bipedEntityRenderState);
                ci.cancel();
                return;
            }
        }
    }

    @Inject(
            method = "positionRightArm",
            at = @At("HEAD"),
            cancellable = true
    )
    public void applyCustomRightArmPose(BipedEntityRenderState bipedEntityRenderState, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (!(bipedEntityRenderState instanceof ExpandedEntityRenderState state)) return;

        if (state.pneumonoCore$getRightArm() == null) return;

        for (CustomArmPoses.Pose pose : CustomArmPoses.getPoses()) {
            if (state.pneumonoCore$getRightArm().equals(pose.id())) {
                pose.rightArm().pose((BipedEntityModel<?>)(Object)this, bipedEntityRenderState);
                ci.cancel();
                return;
            }
        }
    }
}
//?}
