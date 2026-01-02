package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.TextListExtras;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideTextListElement(List<Component> list, TextListExtras extras, ElementPosition position) implements IPageElement {
    public static final MapCodec<GuideTextListElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ComponentSerialization.CODEC.listOf().fieldOf("list").forGetter(GuideTextListElement::list),
            TextListExtras.CODEC.optionalFieldOf("extras", TextListExtras.getDefault()).forGetter(GuideTextListElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideTextListElement::position)
    ).apply(inst, GuideTextListElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
