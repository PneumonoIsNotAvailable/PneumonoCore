package net.pneumono.pneumonocore.datagen;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

//? if >=1.20.2 {
import net.minecraft.data.recipes.RecipeOutput;
//?} else {
/*import net.minecraft.data.recipes.FinishedRecipe;
import java.util.function.Consumer;
*///?}

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class StonecuttingRecipeGenerator {
    private final RecipeProvider recipeProvider;
    private final /*? if >=1.20.2 {*/RecipeOutput/*?} else {*//*Consumer<FinishedRecipe>*//*?}*/ output;
    private final RecipeCategory defaultCategory;
    private final int[] multipliers;
    private final ItemLike[] inputs;

    public StonecuttingRecipeGenerator(
            RecipeProvider recipeProvider,
            /*? if >=1.20.2 {*/RecipeOutput/*?} else {*//*Consumer<FinishedRecipe>*//*?}*/ output,
            ItemLike... inputs
    ) {
        this(recipeProvider, output, RecipeCategory.BUILDING_BLOCKS, inputs);
    }

    public StonecuttingRecipeGenerator(
            RecipeProvider recipeProvider,
            /*? if >=1.20.2 {*/RecipeOutput/*?} else {*//*Consumer<FinishedRecipe>*//*?}*/ output,
            RecipeCategory defaultCategory,
            ItemLike... inputs
    ) {
        this(recipeProvider, output, defaultCategory, createDefaultArray(inputs.length), inputs);
    }

    private static int[] createDefaultArray(int size) {
        int[] array = new int[size];
        Arrays.fill(array, 1);
        return array;
    }

    private StonecuttingRecipeGenerator(
            RecipeProvider recipeProvider,
            /*? if >=1.20.2 {*/RecipeOutput/*?} else {*//*Consumer<FinishedRecipe>*//*?}*/ output,
            RecipeCategory defaultCategory,
            int[] multipliers, ItemLike... inputs
    ) {
        this.recipeProvider = recipeProvider;
        this.output = output;
        this.defaultCategory = defaultCategory;
        this.multipliers = multipliers;
        this.inputs = inputs;
    }

    public StonecuttingRecipeGenerator recipe(ItemLike result) {
        return recipe(this.defaultCategory, result, 1);
    }

    public StonecuttingRecipeGenerator recipe(ItemLike result, int count) {
        return recipe(this.defaultCategory, result, count);
    }

    public StonecuttingRecipeGenerator recipe(RecipeCategory category, ItemLike result) {
        return recipe(category, result, 1);
    }

    public StonecuttingRecipeGenerator recipe(RecipeCategory category, ItemLike result, int count) {
        if (count < 1) throw new IllegalArgumentException("Count cannot be <1");

        for (int i = 0; i < this.inputs.length; ++i) {
            ItemLike input = this.inputs[i];
            int finalCount = this.multipliers[i] * count;

            SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, result, finalCount)
                    .unlockedBy(RecipeProvider.getHasName(input), this.recipeProvider.has(input))
                    .save(this.output, RecipeProvider.getConversionRecipeName(result, input) + "_stonecutting");
        }

        return this;
    }

    public StonecuttingRecipeGenerator createChild(ItemLike newInput) {
        return createChild(newInput, 1);
    }

    public StonecuttingRecipeGenerator createChild(ItemLike newInput, int count) {
        ItemLike[] newInputs = Arrays.copyOf(this.inputs, this.inputs.length + 1);
        newInputs[this.inputs.length] = newInput;

        int[] newMultipliers = Arrays.copyOf(this.multipliers, this.multipliers.length + 1);
        for (int i = 0; i < newMultipliers.length; ++i) {
            newMultipliers[i] = newMultipliers[i] * count;
        }
        newMultipliers[this.multipliers.length] = 1;

        return new StonecuttingRecipeGenerator(this.recipeProvider, this.output, this.defaultCategory, newMultipliers, newInputs);
    }

    public StonecuttingRecipeGenerator createChildWithRecipe(ItemLike newInput) {
        recipe(newInput);
        return createChild(newInput);
    }

    public StonecuttingRecipeGenerator createChildWithRecipe(ItemLike newInput, int count) {
        recipe(newInput, count);
        return createChild(newInput, count);
    }

    public StonecuttingRecipeGenerator createChildWithRecipe(RecipeCategory category, ItemLike newInput) {
        recipe(category, newInput);
        return createChild(newInput);
    }

    public StonecuttingRecipeGenerator createChildWithRecipe(RecipeCategory category, ItemLike newInput, int count) {
        recipe(category, newInput, count);
        return createChild(newInput, count);
    }
}
