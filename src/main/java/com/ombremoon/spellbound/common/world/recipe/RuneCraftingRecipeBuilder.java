package com.ombremoon.spellbound.common.world.recipe;

import com.ombremoon.spellbound.common.magic.effects.EffectHolder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class RuneCraftingRecipeBuilder implements RecipeBuilder {
    private final EffectHolder result;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();

    public RuneCraftingRecipeBuilder(EffectHolder result) {
        this.result = result;
    }

    public static RuneCraftingRecipeBuilder createRune(EffectHolder effect) {
        return new RuneCraftingRecipeBuilder(effect);
    }

    public RuneCraftingRecipeBuilder requires(TagKey<Item> item) {
        return this.requires(Ingredient.of(item));
    }

    public RuneCraftingRecipeBuilder requires(ItemLike item) {
        return this.requires(Ingredient.of(item));
    }

    public RuneCraftingRecipeBuilder requires(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }


    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return null;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return null;
    }


    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id));
        RuneCraftingRecipe recipe = new RuneCraftingRecipe(this.result, this.ingredients);
        recipeOutput.accept(id, recipe, builder.build(id.withPrefix("recipes/rune_crafting/")));
    }
}
