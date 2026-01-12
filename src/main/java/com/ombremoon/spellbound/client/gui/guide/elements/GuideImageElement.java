package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.GuideImageExtras;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideImageElement(ResourceLocation loc, int width, int height, ElementPosition position, GuideImageExtras extras, ResourceLocation corner) implements IPageElement {
    public static final MapCodec<GuideImageElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("image").forGetter(GuideImageElement::loc),
            Codec.INT.fieldOf("width").forGetter(GuideImageElement::width),
            Codec.INT.fieldOf("height").forGetter(GuideImageElement::height),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideImageElement::position),
            GuideImageExtras.CODEC.optionalFieldOf("extras", GuideImageExtras.getDefault()).forGetter(GuideImageElement::extras),
            ResourceLocation.CODEC.optionalFieldOf("corner_texture", CommonClass.customLocation("textures/gui/books/image_borders/studies_in_the_arcane.png")).forGetter(GuideImageElement::corner)
    ).apply(inst, GuideImageElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }

}
