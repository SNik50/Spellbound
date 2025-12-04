package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide_renderers.GuideImageRenderer;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.GuideImageExtras;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideImage(ResourceLocation loc, int width, int height, ElementPosition position, GuideImageExtras extras) implements IPageElement {
    public static final MapCodec<GuideImage> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("image").forGetter(GuideImage::loc),
            Codec.INT.fieldOf("width").forGetter(GuideImage::width),
            Codec.INT.fieldOf("height").forGetter(GuideImage::height),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideImage::position),
            GuideImageExtras.CODEC.optionalFieldOf("extras", GuideImageExtras.getDefault()).forGetter(GuideImage::extras)
    ).apply(inst, GuideImage::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
