package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.StaticItemExtras;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideStaticItemElement(List<Ingredient> item, String tileName, ElementPosition position, StaticItemExtras extras) implements IPageElement {
    public static final MapCodec<GuideStaticItemElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("item").forGetter(GuideStaticItemElement::item),
            Codec.STRING.optionalFieldOf("tileName", "basic").forGetter(GuideStaticItemElement::tileName),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideStaticItemElement::position),
            StaticItemExtras.CODEC.optionalFieldOf("extras", StaticItemExtras.getDefault()).forGetter(GuideStaticItemElement::extras)
    ).apply(inst, GuideStaticItemElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
