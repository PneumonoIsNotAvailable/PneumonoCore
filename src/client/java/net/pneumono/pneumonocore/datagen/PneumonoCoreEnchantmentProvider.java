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
 * Provider for datagen of enchantments.
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

    public static Map<RegistryKey<Enchantment>, Enchantment.Builder> getEnchantmentBuilders(RegistryEntryLookup<Item> itemLookup) {
        throw new IllegalStateException("getEnchantmentBuilders must be overridden");
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper.Impl<Item> itemLookup = /*? if >=1.21.4 {*/registries.getOrThrow(RegistryKeys.ITEM);/*?} else {*//*registries.getWrapperOrThrow(RegistryKeys.ITEM);*//*?}*/

        for (Map.Entry<RegistryKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            entries.add(entry.getKey(), entry.getValue().build(entry.getKey().getValue()));
        }
    }

    public static void bootstrap(Registerable<Enchantment> registry) {
        RegistryEntryLookup<Item> itemLookup = registry.getRegistryLookup(RegistryKeys.ITEM);

        for (Map.Entry<RegistryKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            registry.register(entry.getKey(), entry.getValue().build(entry.getKey().getValue()));
        }
    }

    @Override
    public String getName() {
        return "Enchantments";
    }
}
