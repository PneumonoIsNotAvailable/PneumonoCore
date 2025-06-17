package net.pneumono.pneumonocore;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PneumonoCoreDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnglishLangProvider::new);
    }

    private static class EnglishLangProvider extends FabricLanguageProvider {
        private EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
            builder.add("configs.pneumonocore.screen_title", "PneumonoCore Configs");
            builder.add("configs_screen.pneumonocore.reset_all", "Reset All Configs");
            builder.add("configs_screen.pneumonocore.information", "Information");
            builder.add("configs_screen.pneumonocore.entry_type_error", "Unsupported!");
            builder.add("configs_screen.pneumonocore.no_configs", "This mod has no configs!");
            builder.add("configs_screen.pneumonocore.boolean_enabled", "ON");
            builder.add("configs_screen.pneumonocore.boolean_disabled", "OFF");
            builder.add("configs_screen.pneumonocore.ticks", "T");
            builder.add("configs_screen.pneumonocore.seconds", "S");
            builder.add("configs_screen.pneumonocore.minutes", "M");
            builder.add("configs_screen.pneumonocore.hours", "H");
            builder.add("configs_screen.pneumonocore.days", "D");
            builder.add("configs_screen.pneumonocore.ticks.full", "Ticks");
            builder.add("configs_screen.pneumonocore.seconds.full", "Seconds");
            builder.add("configs_screen.pneumonocore.minutes.full", "Minutes");
            builder.add("configs_screen.pneumonocore.hours.full", "Hours");
            builder.add("configs_screen.pneumonocore.days.full", "Days");
            builder.add("configs_screen.pneumonocore.client", "Client Configuration");
            builder.add("configs_screen.pneumonocore.server", "Server Configuration");
            builder.add("configs.category.pneumonocore.empty", "Uncategorized");
        }
    }
}
