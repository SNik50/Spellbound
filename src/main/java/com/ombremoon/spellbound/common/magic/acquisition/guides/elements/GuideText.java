package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.TextExtras;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.special.IClickable;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.special.IHoverable;
import org.jetbrains.annotations.NotNull;

public record GuideText(String translationKey, TextExtras extras, ElementPosition position) implements IPageElement, IClickable, IHoverable {
    public static final MapCodec<GuideText> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.fieldOf("translation").forGetter(GuideText::translationKey),
            TextExtras.CODEC.optionalFieldOf("extras", TextExtras.getDefault()).forGetter(GuideText::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideText::position)
    ).apply(inst, GuideText::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
