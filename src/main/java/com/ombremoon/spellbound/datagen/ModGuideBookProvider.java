package com.ombremoon.spellbound.datagen;

import com.ombremoon.spellbound.common.init.SBEntities;
import com.ombremoon.spellbound.common.init.SBPageScraps;
import com.ombremoon.spellbound.common.init.SBRituals;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookPage;
import com.ombremoon.spellbound.datagen.provider.GuideBookProvider;
import com.ombremoon.spellbound.datagen.provider.guide_builders.PageBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModGuideBookProvider extends GuideBookProvider {
    //Books
    private static final ResourceLocation RUIN = loc("grimoire_of_annihilation");
    private static final ResourceLocation BASIC = loc("studies_in_the_arcane");
    private static final ResourceLocation TRANSFIG = loc("architects_lexicon");

    //Pages
    private static final ResourceLocation RUIN_P1 = loc("sb_ruin_v1_p1");
    private static final ResourceLocation RUIN_P2 = loc("sb_ruin_v1_p2");
    private static final ResourceLocation RUIN_P3 = loc("sb_ruin_v1_p3");
    private static final ResourceLocation SOLAR_RAY = loc("solar_ray_page");

    private static final ResourceLocation TRANSFIG_P1 = loc("sb_transfig_v1_p1");
    private static final ResourceLocation SHADOW_GATE_ACQ = loc("shadow_gate_page_acq_1");
    private static final ResourceLocation SHADOW_GATE_ACQ_2 = loc("shadow_gate_page_acq_2");

    private static final ResourceLocation BASIC_P1 = loc("sb_basic_v1_p1");

    public ModGuideBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, BiConsumer<ResourceLocation, GuideBookPage> writer) {
        //Basic book
        PageBuilder
                .forBook(BASIC)
                .addElements(
                        PageBuilder.Image
                                .of(loc("textures/gui/books/images/spellbound_logo.png"))
                                .setDimensions(64, 64)
                                .position(40, 20)
                                .disableCorners()
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.basic.discord")
                                .position(55, 100)
                                .setLink("https://discord.gg/hagCkhVwfb")
                                .underline()
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.basic.bugs")
                                .position(42, 115)
                                .setLink("https://github.com/MoonBase-Mods/Spellbound/issues")
                                .underline().build(),
                        PageBuilder.Text
                                .ofTranslatable("item.spellbound.studies_in_the_arcane")
                                .position(PAGE_TWO_START_X, 20)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.basic.blurb")
                                .position(PAGE_TWO_START_X, 40)
                                .build()
                ).save(writer, BASIC_P1);

        //Ruin book
        PageBuilder
                .forBook(RUIN)
                .addElements(
                        PageBuilder.Text
                                .ofTranslatable("item.spellbound.grimoire_of_annihilation")
                                .position(PAGE_TWO_START_X + 13, 20)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.v1_p1.description")
                                .hoverText("Testing this lol")
                                .setLink("https://www.google.com/")
                                .position(PAGE_TWO_START_X, 50)
                                .build()
                ).save(writer, RUIN_P1);
        PageBuilder
                .forBook(RUIN)
                .setPreviousPage(RUIN_P1)
                .addElements(
                        PageBuilder.Image
                                .of(CommonClass.customLocation("textures/gui/books/images/ruin_portal.png"))
                                .setDimensions(140, 74)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.v1_p2.ruin_portal")
                                .position(0, 80)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.v1_p2.keystone")
                                .position(PAGE_TWO_START_X, 5)
                                .build(),
                        PageBuilder.Recipe
                                .of(ResourceLocation.withDefaultNamespace("crafting_table"))
                                .gridName(PageBuilder.Recipe.SpellboundGrids.GRIMOIRE)
                                .position(195, 125)
                                .build()
                ).save(writer, RUIN_P2);
        PageBuilder
                .forBook(RUIN)
                .setPreviousPage(RUIN_P2)
                .addElements(
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.v1_p3.keystones")
                                .build(),
                        PageBuilder.Image
                                .of(loc("textures/gui/books/images/broker_tower.png"))
                                .setDimensions(150, 87)
                                .position(PAGE_TWO_START_X, 0)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.v1_p3.spell_broker")
                                .position(PAGE_TWO_START_X, 95)
                                .build()
                ).save(writer, RUIN_P3);
        PageBuilder
                .forBook(RUIN)
                .setPreviousPage(RUIN_P3)
                .addElements(
                        PageBuilder.Text
                                .ofTranslatable("spells.spellbound.solar_ray")
                                .position(5, 0)
                                .build(),
                        PageBuilder.EntityRenderer
                                .of()
                                .addEntity(SBEntities.SOLAR_RAY.get())
                                .setRequiredScrap(SBPageScraps.UNLOCKED_SOLAR_RAY)
                                .setRotations(0, 70, 10)
                                .scale(12)
                                .position(33, 90).build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.solar_ray.spell_lore")
                                .position(0, 120)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.ruin.solar_ray.stat_lore")
                                .position(PAGE_TWO_START_X, 20)
                                .build(),
                        PageBuilder.SpellInfo
                                .of(SBSpells.SOLAR_RAY.get())
                                .position(PAGE_TWO_START_X, 110)
                                .build()
                ).save(writer, SOLAR_RAY);

        PageBuilder
                .forBook(TRANSFIG)
                .addElements(
                        PageBuilder.Text
                                .ofTranslatable("item.spellbound.architects_lexicon")
                                .position(PAGE_TWO_START_X + 13, 20)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("guide.transfig.v1_p1.description")
                                .hoverText("Testing this lol")
                                .setLink("https://www.google.com/")
                                .position(PAGE_TWO_START_X, 50)
                                .build()
                ).save(writer, TRANSFIG_P1);
        PageBuilder
                .forBook(TRANSFIG)
                .setPreviousPage(TRANSFIG_P1)
                .addElements(
                        PageBuilder.Text
                                .ofTranslatable("ritual.spellbound.create_shadow_gate")
                                .position(PAGE_START_CENTER_X, PAGE_START_DOUBLE_Y)
                                .centered()
                                .bold()
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("spellbound.create_shadow_gate.description")
                                .position(0, 35)
                                .italic()
                                .build(),
                        PageBuilder.Text
                                .ofLiteral("-------------------------")
                                .position(-5, 55)
                                .italic()
                                .build(),
                        PageBuilder.TextList
                                .of()
                                .addEntry(translatable("spellbound.ritual.tier_two"))
                                .addEntry(translatable("spellbound.ritual.activation_time", Integer.toString(20)))
                                .addEntry(translatable("spellbound.ritual.duration").append(translatable("spellbound.ritual.not_applicable")))
                                .position(10, 65)
                                .build(),
                        PageBuilder.Text
                                .ofTranslatable("spellbound.ritual.materials")
                                .position(PAGE_TWO_START_CENTER_X, PAGE_START_Y)
                                .centered()
                                .underline()
                                .build(),
                        PageBuilder.RitualRenderer
                                .of(SBRituals.CREATE_MYSTIC_ARMOR)
                                .build()
                ).save(writer, SHADOW_GATE_ACQ);
    }

    private static MutableComponent translatable(String text) {
        return Component.translatable(text);
    }

    private static MutableComponent translatable(String text, String append) {
        return Component.translatable(text, append);
    }

    private static MutableComponent literal(String text) {
        return Component.literal(text);
    }

    private static ResourceLocation loc(String path) {
        return CommonClass.customLocation(path);
    }

    @Override
    public String getName() {
        return "pages";
    }
}
