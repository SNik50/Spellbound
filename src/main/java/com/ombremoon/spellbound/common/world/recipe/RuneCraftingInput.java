package com.ombremoon.spellbound.common.world.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class RuneCraftingInput implements RecipeInput {
    private final List<ItemStack> items;
    private final int ingredientCount;

    public RuneCraftingInput(List<ItemStack> items) {
        this.items = items;
        int i = 0;

        for (ItemStack itemstack : items) {
            if (!itemstack.isEmpty()) {
                i++;
            }
        }

        this.ingredientCount = i;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public List<ItemStack> items() {
        return this.items;
    }

    public int ingredientCount() {
        return this.ingredientCount;
    }
}
