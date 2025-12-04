package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ItemRendererExtras;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideItemRenderer(ResourceLocation itemLoc, ElementPosition position, ItemRendererExtras extras) implements IPageElement {
    public static final MapCodec<GuideItemRenderer> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("item").forGetter(GuideItemRenderer::itemLoc),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideItemRenderer::position),
            ItemRendererExtras.CODEC.optionalFieldOf("extras", ItemRendererExtras.getDefault()).forGetter(GuideItemRenderer::extras)
    ).apply(inst, GuideItemRenderer::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
