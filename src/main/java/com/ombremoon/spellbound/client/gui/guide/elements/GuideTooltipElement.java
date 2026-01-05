package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record GuideTooltipElement(List<Component> tooltips, ElementPosition position, int width, int height) implements IPageElement {
    public static final MapCodec<GuideTooltipElement> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ComponentSerialization.CODEC.listOf().fieldOf("tooltips").forGetter(GuideTooltipElement::tooltips),
                    ElementPosition.CODEC.fieldOf("position").forGetter(GuideTooltipElement::position),
                    Codec.INT.fieldOf("width").forGetter(GuideTooltipElement::width),
                    Codec.INT.fieldOf("height").forGetter(GuideTooltipElement::height)
            ).apply(instance, GuideTooltipElement::new)
    );

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
