package net.pneumono.pneumonocore.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pneumono.pneumonocore.util.MultiVersionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

//? if >=1.21.4 {
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
//?} else if >=1.20.2 {
/*import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
*///?}

//? if >=1.21.3 {
import net.minecraft.registry.RegistryKey;
import net.minecraft.recipe.Recipe;
//?}

//? if >=1.20.4 {
import net.minecraft.recipe.RawShapedRecipe;
//?}

//? if >=1.20.2 {
import net.minecraft.advancement.AdvancementRequirements;
//?}

// Mostly boilerplate from ShapedRecipeJsonBuilder
// Allows using ItemStacks as results
// Do not use <1.20.4
@SuppressWarnings("unused")
public class ShapedStackRecipeJsonBuilder /*? if >=1.20.2 {*/implements CraftingRecipeJsonBuilder/*?}*/ {
    private final RegistryEntryLookup<Item> registryLookup;
    private final RecipeCategory category;
    private final ItemStack output;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> inputs = Maps.newLinkedHashMap();
    //? if >=1.20.2 {
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    //?}
    @Nullable
    private String group;
    private boolean showNotification = true;

    private ShapedStackRecipeJsonBuilder(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemStack output) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.output = output;
    }

    public static ShapedStackRecipeJsonBuilder create(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, ItemStack output) {
        return new ShapedStackRecipeJsonBuilder(registryLookup, category, output);
    }

    public ShapedStackRecipeJsonBuilder input(Character c, TagKey<Item> tag) {
        //? if >=1.21.8 {
        return this.input(c, Ingredient.ofTag(this.registryLookup.getOrThrow(tag)));
        //?} else if >=1.21.3 {
        /*return this.input(c, Ingredient.fromTag(this.registryLookup.getOrThrow(tag)));
        *///?} else {
        /*return this.input(c, Ingredient.fromTag(tag));
        *///?}
    }

    public ShapedStackRecipeJsonBuilder input(Character c, ItemConvertible item) {
        //? if >=1.21.3 {
        return this.input(c, Ingredient.ofItem(item));
        //?} else {
        /*return this.input(c, Ingredient.ofItems(item));
        *///?}
    }

    public ShapedStackRecipeJsonBuilder input(Character c, Ingredient ingredient) {
        if (this.inputs.containsKey(c)) {
            throw new IllegalArgumentException("Symbol '" + c + "' is already defined!");
        } else if (c == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.inputs.put(c, ingredient);
            return this;
        }
    }

    public ShapedStackRecipeJsonBuilder pattern(String patternStr) {
        if (!this.pattern.isEmpty() && patternStr.length() != MultiVersionUtil.getFirst(this.pattern).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternStr);
            return this;
        }
    }

    //? if >=1.20.2 {
    public ShapedStackRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }
    //?}

    public ShapedStackRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public ShapedStackRecipeJsonBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    //? if >=1.20.2 {
    @Override
    //?}
    public Item getOutputItem() {
        return this.output.getItem();
    }

    //? if >=1.20.2 {
    @Override
    //?}
    public void offerTo(
            /*? if >=1.20.2 {*/RecipeExporter exporter,/*?}*/
            /*? if >=1.21.3 {*/RegistryKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        //? if >=1.20.4 {
        RawShapedRecipe rawShapedRecipe = this.validate(recipeKey);
        Advancement.Builder builder = exporter.getAdvancementBuilder()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(builder::criterion);

        ShapedRecipe shapedRecipe = new ShapedRecipe(
                Objects.requireNonNullElse(this.group, ""),
                CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                rawShapedRecipe,
                this.output,
                this.showNotification
        );
        exporter.accept(recipeKey, shapedRecipe, builder.build(getValue(recipeKey).withPrefixedPath("recipes/" + this.category.getName() + "/")));
        //?}
    }

    //? if >=1.20.4 {
    private RawShapedRecipe validate(
            /*? if >=1.21.3 {*/RegistryKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + getValue(recipeKey));
        } else {
            return RawShapedRecipe.create(this.inputs, this.pattern);
        }
    }
    //?}

    private Identifier getValue(
            /*? if >=1.21.3 {*/RegistryKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        //? if >=1.21.3 {
        return recipeKey.getValue();
        //?} else {
        /*return recipeKey;
        *///?}
    }
}
