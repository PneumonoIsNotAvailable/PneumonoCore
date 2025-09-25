package net.pneumono.pneumonocore.util;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

//? if >=1.21.4 {
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
//?} else {
/*import net.minecraft.client.render.entity.state.BipedEntityRenderState;
*///?}

import java.util.ArrayList;
import java.util.List;

public final class CustomArmPoses {
    private static final List<Pose> POSES = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void addPose(Identifier id, PosePredicate predicate, PoseAction leftArm, PoseAction rightArm, boolean twoHanded) {
        POSES.add(new Pose(id, predicate, leftArm, rightArm, twoHanded));
    }

    public static List<Pose> getPoses() {
        return POSES;
    }

    public record Pose(Identifier id, PosePredicate predicate, PoseAction leftArm, PoseAction rightArm, boolean twoHanded) {
        public boolean shouldPose(
                LivingEntity entity,
                /*? if >=1.21.4 {*/ArmedEntityRenderState/*?} else {*//*BipedEntityRenderState*//*?}*/ state,
                boolean rightArm
                /*? if >=1.21.4 {*/, ItemModelManager itemModelManager/*?}*/
        ) {
            return this.predicate.shouldPose(entity, state, rightArm /*? if >=1.21.4 {*/, itemModelManager/*?}*/);
        }
    }

    @FunctionalInterface
    public interface PosePredicate {
        boolean shouldPose(
                LivingEntity entity,
                /*? if >=1.21.4 {*/ArmedEntityRenderState/*?} else {*//*BipedEntityRenderState*//*?}*/ state,
                boolean rightArm
                /*? if >=1.21.4 {*/, ItemModelManager itemModelManager/*?}*/);
    }

    @FunctionalInterface
    public interface PoseAction {
        void pose(
                BipedEntityModel<?> model,
                /*? if >=1.21.4 {*/ArmedEntityRenderState/*?} else {*//*BipedEntityRenderState*//*?}*/ state
        );
    }
}
