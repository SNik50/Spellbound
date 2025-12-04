package com.ombremoon.spellbound.datagen;

import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookPage;
import com.ombremoon.spellbound.datagen.provider.GuideBookProvider;
import com.ombremoon.spellbound.datagen.provider.guide_builders.PageBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModGuideBookProvider extends GuideBookProvider {
    public ModGuideBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, BiConsumer<ResourceLocation, GuideBookPage> writer) {
        PageBuilder
                .forBook(CommonClass.customLocation("grimoire_of_annihilation"))
                .setPreviousPage(CommonClass.customLocation("sb_ruin_v1_p1"))
                .addElements(
                        PageBuilder.Image
                                .of(CommonClass.customLocation("textures/gui/books/images/ruin_portal.png"))
                                .setDimensions(140, 74)
                                .build(),
                        PageBuilder.Text
                                .of("guide.ruin.v1_p2.ruin_portal")
                                .position(0, 80)
                                .build(),
                        PageBuilder.Text
                                .of("guide.ruin.v1_p2.keystone")
                                .position(PAGE_TWO_START, 5)
                                .setRequiredScrap(CommonClass.customLocation("test_scrap"))
                                .build(),
                        PageBuilder.Recipe
                                .of(ResourceLocation.withDefaultNamespace("anvil"))
                                .gridName(PageBuilder.Recipe.SpellboundGrids.GRIMOIRE)
                                .position(195, 125)
                                .setRequiredScrap(CommonClass.customLocation("anvil"))
                                .build()
                ).save(writer, CommonClass.customLocation("sb_ruin_v1_p2"));

    }
}
