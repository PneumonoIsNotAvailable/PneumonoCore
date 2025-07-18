package net.pneumono.pneumonocore.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.StatType;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.config_api.AbstractConfiguration;
import net.pneumono.pneumonocore.config_api.configurations.EnumConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public class PneumonoCoreTranslationBuilder {
    private final FabricLanguageProvider.TranslationBuilder builder;

    public PneumonoCoreTranslationBuilder(FabricLanguageProvider.TranslationBuilder builder) {
        this.builder = builder;
    }

    public void add(String key, String value) {
        this.builder.add(key, value);
    }

    public void add(Item item, String value) {
        this.builder.add(item, value);
    }

    public void add(Block block, String value) {
        this.builder.add(block, value);
    }

    public void add(EntityType<?> type, String value) {
        this.builder.add(type, value);
    }

    public void add(StatType<?> type, String value) {
        this.builder.add(type, value);
    }

    public void add(StatusEffect effect, String value) {
        this.builder.add(effect, value);
    }

    public void add(TagKey<?> key, String value) {
        this.builder.add(key, value);
    }

    public void add(SoundEvent sound, String value) {
        this.builder.add(sound, value);
    }

    public void add(Path existingLangFile) throws IOException {
        this.builder.add(existingLangFile);
    }

    public void addItemGroup(RegistryKey<ItemGroup> key, String value) {
        this.builder.add(key, value);
    }

    public void addEnchantment(RegistryKey<Enchantment> key, String value) {
        this.builder.addEnchantment(key, value);
    }

    public void addEntityAttribute(RegistryEntry<EntityAttribute> entry, String value) {
        this.builder.add(entry, value);
    }

    public void add(Identifier key, String value) {
        this.builder.add(key, value);
    }

    public void add(Identifier key, String value, String prefix) {
        this.builder.add(key.toTranslationKey(prefix), value);
    }

    public void add(Identifier key, String value, String prefix, String suffix) {
        this.builder.add(key.toTranslationKey(prefix, suffix), value);
    }

    public void addStat(Identifier stat, String value) {
        this.builder.add(stat.toTranslationKey("stat"), value);
    }

    public void addAdvancement(Identifier id, String name, String description) {
        this.builder.add(id.toTranslationKey("advancements", "name"), name);
        this.builder.add(id.toTranslationKey("advancements", "description"), description);
    }

    public void addConfigScreenTitle(String modId, String title) {
        this.builder.add("configs." + modId + ".screen_title", title);
    }

    public void addConfig(AbstractConfiguration<?> config, String name, String tooltip) {
        this.builder.add(config.getTranslationKey(), name);
        this.builder.add(config.getTooltipTranslationKey(), tooltip);
    }

    @Deprecated
    public void addConfig(net.pneumono.pneumonocore.config.AbstractConfiguration<?, ?> config, String name, String tooltip) {
        this.builder.add(config.getTranslationKey(), name);
        this.builder.add(config.getTooltipTranslationKey(), tooltip);
    }

    public <T extends Enum<T>> void addEnumConfig(EnumConfiguration<T> config, String name, String tooltip, String... values) {
        this.addConfig(config, name, tooltip);

        T[] keys = config.getDefaultValue().getDeclaringClass().getEnumConstants();
        if (keys.length != values.length) throw new IllegalArgumentException("The number of enum values and translation strings must match!");

        for (int i = 0; i < keys.length; ++i) {
            this.builder.add(config.getTranslationKey(keys[i].toString().toLowerCase()), values[i]);
        }
    }

    @Deprecated
    public <T extends Enum<T>> void addEnumConfig(net.pneumono.pneumonocore.config.EnumConfiguration<T> config, String name, String tooltip, String... values) {
        this.addConfig(config, name, tooltip);

        T[] keys = config.getDefaultValue().getDeclaringClass().getEnumConstants();
        if (keys.length != values.length) throw new IllegalArgumentException("The number of enum values and translation strings must match!");

        for (int i = 0; i < keys.length; ++i) {
            this.builder.add(config.getTranslationKey(keys[i].toString().toLowerCase()), values[i]);
        }
    }

    public void addConfigCategory(String modId, String category, String name) {
        this.builder.add("configs.category." + modId + "." + category, name);
    }

    public void addModMenuTranslations(String modId, String name, String summary) {
        this.addModMenuTranslations(modId, name, summary, summary);
    }

    public void addModMenuTranslations(String modId, String name, String summary, String description) {
        this.builder.add("modmenu.nameTranslation." + modId, name);
        this.builder.add("modmenu.summaryTranslation." + modId, summary);
        this.builder.add("modmenu.descriptionTranslation." + modId, description);
    }

    public <T> BiConsumer<T, String> createBuilder(Function<T, String> keyFunction) {
        return (object, value) -> this.builder.add(keyFunction.apply(object), value);
    }
}
