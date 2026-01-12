package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.EntityRendererExtras;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record GuideEntityElement(List<ResourceLocation> entityLoc, EntityRendererExtras extras, ElementPosition position) implements IPageElement {
    public static final MapCodec<GuideEntityElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.listOf().fieldOf("entities").forGetter(GuideEntityElement::entityLoc),
            EntityRendererExtras.CODEC.optionalFieldOf("extras", EntityRendererExtras.getDefault()).forGetter(GuideEntityElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideEntityElement::position)
    ).apply(inst, GuideEntityElement::new));

    public static final StreamCodec<ByteBuf, GuideEntityElement> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC.codec());

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
