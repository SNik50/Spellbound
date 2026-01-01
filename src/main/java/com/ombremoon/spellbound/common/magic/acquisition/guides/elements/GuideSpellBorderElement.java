package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideSpellBorderElement(ResourceLocation spell, int topGap, ElementPosition position, ResourceLocation path) implements IPageElement {
    public static final MapCodec<GuideSpellBorderElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("spell").forGetter(GuideSpellBorderElement::spell),
            Codec.INT.fieldOf("topGap").forGetter(GuideSpellBorderElement::topGap),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideSpellBorderElement::position),
            ResourceLocation.CODEC.fieldOf("pathTexture").forGetter(GuideSpellBorderElement::path)
    ).apply(inst, GuideSpellBorderElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
