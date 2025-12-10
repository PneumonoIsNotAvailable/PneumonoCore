package net.pneumono.pneumonocore.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.pneumono.pneumonocore.util.MultiVersionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//? if >=1.21.11 {
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
//?} else {
/*import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
*///?}

//? if >=1.21.4 {
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
//?} else if >=1.20.2 {
/*import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
*///?}

//? if >=1.21.2 {
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
//?}

//? if >=1.20.3 {
import net.minecraft.world.item.crafting.ShapedRecipePattern;
//?}

//? if >=1.20.2 {
import net.minecraft.advancements.AdvancementRequirements;
//?}

// Mostly boilerplate from ShapedRecipeBuilder
// Allows using ItemStacks as results
// Do not use <1.20.3
@SuppressWarnings("unused")
public class ShapedStackRecipeBuilder /*? if >=1.20.2 {*/implements RecipeBuilder/*?}*/ {
    private final HolderGetter<Item> registryLookup;
    private final RecipeCategory category;
    private final ItemStack output;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> inputs = Maps.newLinkedHashMap();
    //? if >=1.20.2 {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    //?}
    @Nullable
    private String group;
    private boolean showNotification = true;

    private ShapedStackRecipeBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, ItemStack output) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.output = output;
    }

    public static ShapedStackRecipeBuilder create(HolderGetter<Item> registryLookup, RecipeCategory category, ItemStack output) {
        return new ShapedStackRecipeBuilder(registryLookup, category, output);
    }

    public ShapedStackRecipeBuilder input(Character c, TagKey<Item> tag) {
        //? if >=1.21.2 {
        return this.input(c, Ingredient.of(this.registryLookup.getOrThrow(tag)));
        //?} else {
        /*return this.input(c, Ingredient.of(tag));
        *///?}
    }

    public ShapedStackRecipeBuilder input(Character c, ItemLike item) {
        return this.input(c, Ingredient.of(item));
    }

    public ShapedStackRecipeBuilder input(Character c, Ingredient ingredient) {
        if (this.inputs.containsKey(c)) {
            throw new IllegalArgumentException("Symbol '" + c + "' is already defined!");
        } else if (c == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.inputs.put(c, ingredient);
            return this;
        }
    }

    public ShapedStackRecipeBuilder pattern(String patternStr) {
        if (!this.pattern.isEmpty() && patternStr.length() != MultiVersionUtil.getFirst(this.pattern).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternStr);
            return this;
        }
    }

    //? if >=1.20.2 {
    @Override
    public @NotNull ShapedStackRecipeBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }
    //?}

    public @NotNull ShapedStackRecipeBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    public ShapedStackRecipeBuilder showNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    //? if >=1.20.2 {
    @Override
    //?}
    public @NotNull Item getResult() {
        return this.output.getItem();
    }

    //? if >=1.20.2 {
    @Override
    //?}
    public void save(
            /*? if >=1.20.2 {*/RecipeOutput exporter,/*?}*/
            /*? if >=1.21.2 {*/ResourceKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        //? if >=1.20.3 {
        ShapedRecipePattern rawShapedRecipe = this.validate(recipeKey);
        Advancement.Builder builder = exporter.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey))
                .rewards(AdvancementRewards.Builder.recipe(recipeKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);

        ShapedRecipe shapedRecipe = new ShapedRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                rawShapedRecipe,
                this.output,
                this.showNotification
        );
        exporter.accept(recipeKey, shapedRecipe, builder.build(getValue(recipeKey).withPrefix("recipes/" + this.category.getFolderName() + "/")));
        //?}
    }

    //? if >=1.20.3 {
    private ShapedRecipePattern validate(
            /*? if >=1.21.2 {*/ResourceKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + getValue(recipeKey));
        } else {
            return ShapedRecipePattern.of(this.inputs, this.pattern);
        }
    }
    //?}

    private Identifier getValue(
            /*? if >=1.21.2 {*/ResourceKey<Recipe<?>>/*?} else {*//*Identifier*//*?}*/ recipeKey
    ) {
        //? if >=1.21.11 {
        return recipeKey.identifier();
        //?} else if >=1.21.2 {
        /*return recipeKey.location();
        *///?} else {
        /*return recipeKey;
        *///?}
    }
}
