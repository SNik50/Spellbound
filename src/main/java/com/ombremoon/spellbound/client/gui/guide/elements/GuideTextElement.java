package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.TextExtras;
import com.ombremoon.spellbound.client.gui.guide.elements.special.IClickable;
import com.ombremoon.spellbound.client.gui.guide.elements.special.IHoverable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import org.jetbrains.annotations.NotNull;

public record GuideTextElement(Component text, TextExtras extras, ElementPosition position) implements IPageElement, IClickable, IHoverable {
    public static final MapCodec<GuideTextElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ComponentSerialization.CODEC.fieldOf("translation").forGetter(GuideTextElement::text),
            TextExtras.CODEC.optionalFieldOf("extras", TextExtras.getDefault()).forGetter(GuideTextElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideTextElement::position)
    ).apply(inst, GuideTextElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
