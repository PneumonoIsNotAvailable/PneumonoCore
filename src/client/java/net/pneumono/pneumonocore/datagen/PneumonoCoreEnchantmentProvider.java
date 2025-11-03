package net.pneumono.pneumonocore.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

//? if >=1.21 {
import net.minecraft.data.worldgen.BootstrapContext;
//?}

/**
 * Provider for datagen of enchantments. Should not be used <1.21, for obvious reasons
 *
 * <p>Subclasses must extend {@link #getEnchantmentBuilders}.
 *
 * <p>This may also need to be bootstrapped, by calling {@link #bootstrap} in {@link DataGeneratorEntrypoint#buildRegistry}
 */
@SuppressWarnings("unused")
public abstract class PneumonoCoreEnchantmentProvider extends FabricDynamicRegistryProvider {
    public PneumonoCoreEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    //? if >=1.21 {
    public static Map<ResourceKey<Enchantment>, Enchantment.Builder> getEnchantmentBuilders(HolderGetter<Item> itemLookup) {
        throw new IllegalStateException("getEnchantmentBuilders must be overridden");
    }
    //?} else {
    /*public static Map<ResourceKey<Enchantment>, Enchantment> getEnchantmentBuilders(HolderGetter<Item> itemLookup) {
        throw new UnsupportedOperationException("PneumonoCoreEnchantmentProvider cannot be used in versions without data-driven enchantments");
    }
    *///?}

    @Override
    protected void configure(HolderLookup.Provider lookupProvider, Entries entries) {
        HolderLookup.RegistryLookup<Item> itemLookup = lookupProvider.lookupOrThrow(Registries.ITEM);

        //? if >=1.21 {
        for (Map.Entry<ResourceKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            entries.add(entry.getKey(), entry.getValue().build(entry.getKey().location()));
        }
        //?}
    }

    //? if >=1.21 {
    public static void bootstrap(BootstrapContext<Enchantment> bootstrapContext) {
        HolderGetter<Item> itemLookup = bootstrapContext.lookup(Registries.ITEM);

        for (Map.Entry<ResourceKey<Enchantment>, Enchantment.Builder> entry : getEnchantmentBuilders(itemLookup).entrySet()) {
            bootstrapContext.register(entry.getKey(), entry.getValue().build(entry.getKey().location()));
        }
    }
    //?}

    @Override
    public @NotNull String getName() {
        return "Enchantments";
    }
}
