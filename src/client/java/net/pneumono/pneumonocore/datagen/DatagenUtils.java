package net.pneumono.pneumonocore.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.pneumono.pneumonocore.config.*;

@SuppressWarnings("unused")
public class DatagenUtils {
    public static void generateConfigScreenTranslation(FabricLanguageProvider.TranslationBuilder builder, String modID, String name) {
        builder.add("configs." + modID + ".screen_title", name);
    }

    public static void generateConfigTranslations(FabricLanguageProvider.TranslationBuilder builder, AbstractConfiguration<?> config, String name, String tooltip) {
        builder.add(config.getTranslationKey(), name);
        builder.add(config.getTranslationKey() + ".tooltip", tooltip);
    }

    public static <T extends Enum<T>> void generateEnumConfigOptionTranslation(FabricLanguageProvider.TranslationBuilder builder, EnumConfiguration<T> config, T value, String name) {
        builder.add(config.getTranslationKey() + "." + value.toString().toLowerCase(), name);
    }
}
