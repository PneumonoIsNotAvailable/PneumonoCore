package net.pneumono.pneumonocore.mixin.client;

//? if >=1.21.4 {
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.util.CustomArmPoses;
import net.pneumono.pneumonocore.util.ExpandedEntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmedEntityRenderState.class)
public abstract class EntityRenderStateMixin implements ExpandedEntityRenderState {
    @Nullable
    @Unique
    private Identifier leftArmPose = null;
    @Nullable
    @Unique
    private Identifier rightArmPose = null;

    @Inject(
            method = "updateRenderState",
            at = @At("RETURN")
    )
    private static void updateExpandedRenderState(LivingEntity entity, ArmedEntityRenderState armedState, ItemModelManager itemModelManager, CallbackInfo ci) {
        if (!(armedState instanceof ExpandedEntityRenderState state)) return;

        state.pneumonoCore$setLeftArm(null);
        state.pneumonoCore$setRightArm(null);

        boolean isLeftSet = true;
        boolean isRightSet = true;
        for (CustomArmPoses.Pose pose : CustomArmPoses.getPoses()) {
            if (pose.twoHanded() && pose.shouldPose(entity, armedState, false, itemModelManager)) {
                state.pneumonoCore$setLeftArm(pose.id());
                state.pneumonoCore$setRightArm(pose.id());
                return;
            }

            if (isLeftSet && pose.shouldPose(entity, armedState, false, itemModelManager)) {
                state.pneumonoCore$setLeftArm(pose.id());
                isLeftSet = false;
            }
            if (isRightSet && pose.shouldPose(entity, armedState, true, itemModelManager)) {
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
//?}
