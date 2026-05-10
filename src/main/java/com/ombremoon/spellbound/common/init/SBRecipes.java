package com.ombremoon.spellbound.common.init;

import com.ombremoon.spellbound.common.world.recipe.RuneCraftingRecipe;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SBRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Constants.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Constants.MOD_ID);

    public static final Supplier<RecipeSerializer<RuneCraftingRecipe>> RUNE_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("rune_crafting", RuneCraftingRecipe.Serializer::new);

    public static final Supplier<RecipeType<RuneCraftingRecipe>> RUNE_RECIPE = RECIPE_TYPES.register("rune_crafting", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "rune_crafting";
        }
    });

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
    }
}
