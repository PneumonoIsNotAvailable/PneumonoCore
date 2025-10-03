package net.pneumono.pneumonocore.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Provider for datagen of enchantments. Should not be used <1.21.1, for obvious reasons
 *
 * <p>Subclasses must extend {@link #getEnchantmentBuilders}.
 *
 * <p>This may also need to be bootstrapped, by calling {@link #bootstrap} in {@link DataGeneratorEntrypoint#buildRegistry}
 */
@SuppressWarnings("unused")
public abstract class PneumonoCoreEnchantmentProvider extends FabricDynamicRegistryProvider {
    public PneumonoCoreEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    //? if >=1.21.1 {
    public static Map<RegistryKey<Enchantment>, Enchantment.Builder> getEnchantmentBuilders(RegistryEntryLookup<Item> itemLookup) {
        throw new IllegalStateException("getEnchantmentBuilders must be overridden");
    }
    //?} else {
    /*public static Map<RegistryKey<Enchantment>, Enchantment> getEnchantmentBuilders(RegistryEntryLookup<Item> itemLookup) {
        throw new UnsupportedOperationException("PneumonoCoreEnchantmentProvider cannot be used in versions without data-driven enchantments");
    }
    *///?}

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        //? if >=1.21.3 {
        RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
        //?} else {
        /*RegistryWrapper.Impl<Item> itemLookup = registries.getWrapperOrThrow(RegistryKeys.ITEM);
        *///?}

        //? if >=1.21.1 {
        for (Map.Entry<RegistryKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            entries.add(entry.getKey(), entry.getValue().build(entry.getKey().getValue()));
        }
        //?}
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        //? if >=1.21.1 {
        RegistryEntryLookup<Item> itemLookup = registry.getRegistryLookup(RegistryKeys.ITEM);

        for (Map.Entry<RegistryKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            registry.register(entry.getKey(), entry.getValue().build(entry.getKey().getValue()));
        }
        //?}
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}
