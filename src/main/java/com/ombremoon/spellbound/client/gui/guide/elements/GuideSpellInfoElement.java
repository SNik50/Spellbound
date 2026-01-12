package com.ombremoon.spellbound.client.gui.guide.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.ElementPosition;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.SpellInfoExtras;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideSpellInfoElement(ResourceLocation spellLoc, SpellInfoExtras extras, ElementPosition position) implements IPageElement {

    public static final MapCodec<GuideSpellInfoElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("path").forGetter(GuideSpellInfoElement::spellLoc),
            SpellInfoExtras.CODEC.optionalFieldOf("extras", SpellInfoExtras.getDefault()).forGetter(GuideSpellInfoElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideSpellInfoElement::position)
    ).apply(inst, GuideSpellInfoElement::new));

    public static final StreamCodec<ByteBuf, GuideSpellInfoElement> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC.codec());

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }

}
