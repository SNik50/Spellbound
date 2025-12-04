package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.StaticItemExtras;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideStaticItem(ResourceLocation itemLoc, String tileName, ElementPosition position, StaticItemExtras extras) implements IPageElement {
    public static final MapCodec<GuideStaticItem> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("item").forGetter(GuideStaticItem::itemLoc),
            Codec.STRING.optionalFieldOf("tileName", "basic").forGetter(GuideStaticItem::tileName),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideStaticItem::position),
            StaticItemExtras.CODEC.optionalFieldOf("extras", StaticItemExtras.getDefault()).forGetter(GuideStaticItem::extras)
    ).apply(inst, GuideStaticItem::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
