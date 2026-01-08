package com.ombremoon.spellbound.datagen;

import com.ombremoon.spellbound.common.init.SBBlocks;
import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.init.SBItems;
import com.ombremoon.spellbound.common.init.SBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(pOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.MAGIC_ESSENCE.get(), 2)
                .requires(SBBlocks.ARCANTHUS.get())
                .unlockedBy("has_arcanthus", has(SBBlocks.ARCANTHUS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.RITUAL_TALISMAN.get())
                .define('G', Items.GOLD_INGOT)
                .define('M', SBItems.MAGIC_ESSENCE.get())
                .pattern("GGG")
                .pattern(" M ")
                .pattern(" G ")
                .unlockedBy("has_magic_essence", has(SBItems.MAGIC_ESSENCE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.TRANSFIGURATION_PEDESTAL.get())
                .define('W', Items.GREEN_WOOL)
                .define('L', ItemTags.LOGS)
                .define('S', ItemTags.WOODEN_SLABS)
                .define('M', SBItems.MAGIC_ESSENCE.get())
                .pattern("SWS")
                .pattern(" M ")
                .pattern("SLS")
                .unlockedBy("has_magic_essence", has(SBItems.MAGIC_ESSENCE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.TRANSFIGURATION_DISPLAY.get())
                .define('S', ItemTags.STONE_BRICKS)
                .define('C', Items.COPPER_INGOT)
                .define('#', SBItems.MAGIC_ESSENCE.get())
                .pattern(" S ")
                .pattern("C#C")
                .pattern("SSS")
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.CHALK.get())
                .define('C', Items.CALCITE)
                .define('M', SBItems.MAGIC_ESSENCE.get())
                .pattern("C  ")
                .pattern(" M ")
                .pattern("  C")
                .unlockedBy("has_magic_essence", has(SBItems.MAGIC_ESSENCE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.MAGI_WORKBENCH.get())
                .define('L', ItemTags.LOGS)
                .define('W', Items.BLUE_WOOL)
                .define('#', SBItems.MAGIC_ESSENCE.get())
                .define('^', Items.BOOK)
                .pattern("^#^")
                .pattern("WWW")
                .pattern("L L")
                .unlockedBy("has_blue_wool", has(Items.BLUE_WOOL))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.RUIN_BOOK.get())
                .define('B', Items.BOOK)
                .define('*', Items.COAL)
                .define('#', Items.SNOWBALL)
                .define('^', Items.COPPER_INGOT)
                .pattern(" # ")
                .pattern("*B^")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.TRANSFIG_BOOK.get())
                .define('B', Items.BOOK)
                .define('*', Items.AMETHYST_SHARD)
                .define('^', Items.GLASS_BOTTLE)
                .pattern("*B^")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.SUMMON_BOOK.get())
                .define('B', Items.BOOK)
                .define('*', Items.BONE)
                .define('^', Items.ROTTEN_FLESH)
                .pattern("*B^")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.DIVINE_BOOK.get())
                .define('B', Items.BOOK)
                .define('*', Items.FERMENTED_SPIDER_EYE)
                .define('^', Items.GOLD_NUGGET)
                .pattern("*B^")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.DECEPTION_BOOK.get())
                .define('B', Items.BOOK)
                .define('*', Items.TRIPWIRE_HOOK)
                .define('^', Items.INK_SAC)
                .pattern("*B^")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(output);
    }
}
