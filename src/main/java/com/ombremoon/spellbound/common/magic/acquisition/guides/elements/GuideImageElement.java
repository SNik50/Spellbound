package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.GuideImageExtras;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideImageElement(ResourceLocation loc, int width, int height, ElementPosition position, GuideImageExtras extras) implements IPageElement {
    public static final MapCodec<GuideImageElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("image").forGetter(GuideImageElement::loc),
            Codec.INT.fieldOf("width").forGetter(GuideImageElement::width),
            Codec.INT.fieldOf("height").forGetter(GuideImageElement::height),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideImageElement::position),
            GuideImageExtras.CODEC.optionalFieldOf("extras", GuideImageExtras.getDefault()).forGetter(GuideImageElement::extras)
    ).apply(inst, GuideImageElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
