package com.ombremoon.spellbound.client.gui.guide.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;

public record ItemListExtras(ResourceLocation pageScrap, int maxRows, int rowGap, int columnGap, int countGap, boolean dropShadow, int textColour, boolean centered) implements IElementExtra {
    public static final Codec<ItemListExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", CommonClass.customLocation("default")).forGetter(ItemListExtras::pageScrap),
            Codec.INT.optionalFieldOf("maxRows", 0).forGetter(ItemListExtras::maxRows),
            Codec.INT.optionalFieldOf("rowGap", 20).forGetter(ItemListExtras::rowGap),
            Codec.INT.optionalFieldOf("columnGap", 45).forGetter(ItemListExtras::columnGap),
            Codec.INT.optionalFieldOf("countGap", 33).forGetter(ItemListExtras::countGap),
            Codec.BOOL.optionalFieldOf("dropShadow", false).forGetter(ItemListExtras::dropShadow),
            Codec.INT.optionalFieldOf("textColour", 0).forGetter(ItemListExtras::textColour),
            Codec.BOOL.optionalFieldOf("centered", false).forGetter(ItemListExtras::centered)
    ).apply(inst, ItemListExtras::new));

    public static ItemListExtras getDefault() {
        return new ItemListExtras(CommonClass.customLocation("default"), 0, 20, 45, 33, true, 0, false);
    }


}
