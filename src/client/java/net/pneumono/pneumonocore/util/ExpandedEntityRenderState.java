package net.pneumono.pneumonocore.util;

import net.minecraft.util.Identifier;

public interface ExpandedEntityRenderState {
    void pneumonoCore$setLeftArm(Identifier id);

    void pneumonoCore$setRightArm(Identifier id);

    Identifier pneumonoCore$getLeftArm();

    Identifier pneumonoCore$getRightArm();
}
