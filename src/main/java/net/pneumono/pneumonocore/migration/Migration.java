package net.pneumono.pneumonocore.migration;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Migration {
    private static final Map<String, Supplier<String>> BLOCK_ENTITIES = new HashMap<>();
    private static final Map<String, Supplier<String>> BLOCKS = new HashMap<>();
    private static final Map<String, Supplier<String>> ITEMS = new HashMap<>();

    public static void registerBlockEntityMigration(Identifier oldId, BlockEntityType<?> newBlockEntity) {
        BLOCK_ENTITIES.put(oldId.toString(), () -> Objects.requireNonNull(Registries.BLOCK_ENTITY_TYPE.getId(newBlockEntity)).toString());
    }

    public static void registerBlockMigration(Identifier oldId, Block newBlock) {
        BLOCKS.put(oldId.toString(), () -> Registries.BLOCK.getId(newBlock).toString());
    }

    public static void registerItemMigration(Identifier oldId, Item newItem) {
        ITEMS.put(oldId.toString(), () -> Registries.ITEM.getId(newItem).toString());
    }

    public static Map<String, Supplier<String>> getBlockEntities() {
        return BLOCK_ENTITIES;
    }

    public static Map<String, Supplier<String>> getBlocks() {
        return BLOCKS;
    }

    public static Map<String, Supplier<String>> getItems() {
        return ITEMS;
    }
}
