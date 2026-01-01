package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.EntityRendererExtras;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideEntityElement(List<ResourceLocation> entityLoc, EntityRendererExtras extras, ElementPosition position) implements IPageElement {
    public static final MapCodec<GuideEntityElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.listOf().fieldOf("entities").forGetter(GuideEntityElement::entityLoc),
            EntityRendererExtras.CODEC.optionalFieldOf("extras", EntityRendererExtras.getDefault()).forGetter(GuideEntityElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideEntityElement::position)
    ).apply(inst, GuideEntityElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
