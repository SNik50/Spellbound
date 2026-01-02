package com.ombremoon.spellbound.common.init;

import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookPage;
import com.ombremoon.spellbound.common.magic.acquisition.transfiguration.TransfigurationRitual;
import com.ombremoon.spellbound.datagen.provider.guide_builders.PageBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.main.Keys;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface SBGuidePages {
    ResourceLocation BASIC = loc("studies_in_the_arcane");
    ResourceLocation TRANSFIG = loc("architects_lexicon");
    ResourceLocation RUIN = loc("grimoire_of_annihilation");
    int PAGE_TWO_START_X = 169;
    int PAGE_START_Y = 8;
    int PAGE_START_DOUBLE_Y = 4;
    int PAGE_START_CENTER_X = 72;
    int PAGE_TWO_START_CENTER_X = 247;

    ResourceKey<GuideBookPage> RUIN_P1 = key("sb_ruin_v1_p1");
    ResourceKey<GuideBookPage> RUIN_P2 = key("sb_ruin_v1_p2");
    ResourceKey<GuideBookPage> RUIN_P3 = key("sb_ruin_v1_p3");
    ResourceKey<GuideBookPage> SOLAR_RAY = key("solar_ray_page");
    ResourceKey<GuideBookPage> TRANSFIG_P1 = key("sb_transfig_v1_p1");
    ResourceKey<GuideBookPage> STRIDE_ACQ = key("stride_page_acq");
    ResourceKey<GuideBookPage> SHADOW_GATE_ACQ = key("shadow_gate_page_acq");
    ResourceKey<GuideBookPage> MYSTIC_ARMOR_ACQ = key("mystic_armor_page_acq");
    ResourceKey<GuideBookPage> BASIC_P1 = key("sb_basic_v1_p1");

    static void bootstrap(BootstrapContext<GuideBookPage> context) {
        register(
                context,
                BASIC_P1,
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
                        )
        );
        register(
                context,
                RUIN_P1,
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
                        )
        );
        register(
                context,
                RUIN_P2,
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
                        )
        );
        register(
                context,
                RUIN_P3,
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
                        )
        );
        register(
                context,
                SOLAR_RAY,
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
                        )
        );
        register(
                context,
                TRANSFIG_P1,
                PageBuilder
                        .forBook(TRANSFIG)
                        .addElements(
                                PageBuilder.Image
                                        .of(loc("textures/gui/paths/transfiguration.png"))
                                        .setDimensions(150, 150)
                                        .position(0, 25)
                                        .disableCorners()
                                        .build(),
                                PageBuilder.Text
                                        .ofTranslatable("item.spellbound.architects_lexicon")
                                        .position(PAGE_TWO_START_CENTER_X, PAGE_START_Y)
                                        .centered()
                                        .build(),
                                PageBuilder.Text
                                        .ofTranslatable("guide.transfig.v1_p1.description")
                                        .position(PAGE_TWO_START_CENTER_X, 65)
                                        .centered()
                                        .build()
                        )
        );
        createRitualPage(context, STRIDE_ACQ, TRANSFIG_P1, SBRituals.CREATE_STRIDE, 5, 0, RitualTier.ONE);
        createRitualPage(context, SHADOW_GATE_ACQ, STRIDE_ACQ, SBRituals.CREATE_SHADOW_GATE, 10, 0, RitualTier.TWO);
        createRitualPage(context, MYSTIC_ARMOR_ACQ, SHADOW_GATE_ACQ, SBRituals.CREATE_MYSTIC_ARMOR, 10, 0, RitualTier.TWO);
    }

    private static void createRitualPage(BootstrapContext<GuideBookPage> context,
                                         ResourceKey<GuideBookPage> currentPage,
                                         ResourceKey<GuideBookPage> prevPage,
                                         ResourceKey<TransfigurationRitual> key,
                                         int activationTime,
                                         int duration,
                                         RitualTier tier
    ) {
        register(
            context,
            currentPage,
            PageBuilder
                    .forBook(TRANSFIG)
                    .setPreviousPage(prevPage)
                    .addElements(
                            PageBuilder.Text
                                    .ofTranslatable("ritual.spellbound." + key.location().getPath())
                                    .position(PAGE_START_CENTER_X, PAGE_START_DOUBLE_Y)
                                    .centered()
                                    .bold()
                                    .build(),
                            PageBuilder.Text
                                    .ofTranslatable("ritual.spellbound." + key.location().getPath() + ".description")
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
                                    .addEntry(translatable("spellbound.ritual.tier_" + tier.name))
                                    .addEntry(translatable("spellbound.ritual.activation_time", Integer.toString(activationTime)))
                                    .addEntry(duration > 0 ? translatable("spellbound.ritual.duration", Integer.toString(duration)) : translatable("spellbound.ritual.duration_not_applicable"))
                                    .position(10, 65)
                                    .build(),
                            PageBuilder.Text
                                    .ofTranslatable("spellbound.ritual.materials")
                                    .position(PAGE_TWO_START_CENTER_X, PAGE_START_Y)
                                    .centered()
                                    .underline()
                                    .build(),
                            PageBuilder.RitualRenderer
                                    .of(key)
                                    .build()
                    )
        );
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

    private static void register(BootstrapContext<GuideBookPage> context, ResourceKey<GuideBookPage> key, PageBuilder builder) {
        context.register(key, builder.build());
    }

    private static ResourceKey<GuideBookPage> key(String name) {
        return ResourceKey.create(Keys.GUIDE_BOOK, CommonClass.customLocation(name));
    }

    enum RitualTier {
        ONE("one"),
        TWO("two"),
        THREE("three");

        private final String name;

        RitualTier(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
