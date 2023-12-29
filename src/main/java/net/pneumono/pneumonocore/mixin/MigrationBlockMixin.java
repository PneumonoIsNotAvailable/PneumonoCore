package net.pneumono.pneumonocore.mixin;

import net.minecraft.registry.Registries;
import net.minecraft.registry.SimpleDefaultedRegistry;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.PneumonoCore;
import net.pneumono.pneumonocore.migration.Migration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SimpleDefaultedRegistry.class)
public abstract class MigrationBlockMixin {
    @ModifyVariable(method = "get(Lnet/minecraft/util/Identifier;)Ljava/lang/Object;", at = @At("HEAD"))
    @SuppressWarnings("unused")
    private Identifier getMigrated(Identifier identifier) {
        if (this == Registries.BLOCK) {
            String oldId = identifier.toString();
            if (Migration.getBlocks().containsKey(oldId)) {
                String newId = Migration.getBlocks().get(oldId).get();
                PneumonoCore.LOGGER.info("Block \"'{}'\" successfully migrated to \"'{}'\"", oldId, newId);

                return new Identifier(newId);
            }
        }

        return identifier;
    }
}
