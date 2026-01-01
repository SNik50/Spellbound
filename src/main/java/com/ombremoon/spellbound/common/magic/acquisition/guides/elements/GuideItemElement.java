package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ItemRendererExtras;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideItemElement(List<Ingredient> items, ElementPosition position, ItemRendererExtras extras) implements IPageElement {
    public static final MapCodec<GuideItemElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("items").forGetter(GuideItemElement::items),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideItemElement::position),
            ItemRendererExtras.CODEC.optionalFieldOf("extras", ItemRendererExtras.getDefault()).forGetter(GuideItemElement::extras)
    ).apply(inst, GuideItemElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
