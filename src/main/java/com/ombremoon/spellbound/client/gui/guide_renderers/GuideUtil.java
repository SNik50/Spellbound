package com.ombremoon.spellbound.client.gui.guide_renderers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public class GuideUtil {

    public static Ingredient buildIngredient(List<Ingredient> ingredients) {
        List<ItemLike> list = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            for (ItemStack stack : ing.getItems()) {
                list.add(stack.getItem());
            }
        }

        return Ingredient.of(list.toArray(new ItemLike[]{}));
    }
}
