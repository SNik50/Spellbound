package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.TextListExtras;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideTextListElement(List<String> list, TextListExtras extras, ElementPosition position) implements IPageElement {
    public static final MapCodec<GuideTextListElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.listOf().fieldOf("list").forGetter(GuideTextListElement::list),
            TextListExtras.CODEC.optionalFieldOf("extras", TextListExtras.getDefault()).forGetter(GuideTextListElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideTextListElement::position)
    ).apply(inst, GuideTextListElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
