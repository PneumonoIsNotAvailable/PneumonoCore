package net.pneumono.pneumonocore.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.Map;

@SuppressWarnings("unused")
public abstract class ModdedBlockFamilies {
    public static BlockFamily.Builder register(Map<Block, BlockFamily> map, Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        map.put(baseBlock, builder.build());
        if (map.containsKey(baseBlock)) {
            throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(baseBlock));
        } else {
            map.put(baseBlock, builder.build());
            return builder;
        }
    }
}
