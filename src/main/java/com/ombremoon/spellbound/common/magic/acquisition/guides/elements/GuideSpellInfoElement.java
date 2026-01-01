package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.SpellInfoExtras;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record GuideSpellInfoElement(ResourceLocation spellLoc, SpellInfoExtras extras, ElementPosition position) implements IPageElement {

    public static final MapCodec<GuideSpellInfoElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("spell").forGetter(GuideSpellInfoElement::spellLoc),
            SpellInfoExtras.CODEC.optionalFieldOf("extras", SpellInfoExtras.getDefault()).forGetter(GuideSpellInfoElement::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideSpellInfoElement::position)
    ).apply(inst, GuideSpellInfoElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }

}
