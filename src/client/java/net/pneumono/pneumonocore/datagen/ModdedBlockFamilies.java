package net.pneumono.pneumonocore.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class ModdedBlockFamilies {
    public static BlockFamily.Builder register(Map<Block, BlockFamily> map, Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        map.put(baseBlock, builder.getFamily());
        if (map.containsKey(baseBlock)) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getId(baseBlock));
        } else {
            map.put(baseBlock, builder.getFamily());
            return builder;
        }
    }
}
