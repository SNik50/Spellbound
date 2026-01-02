package com.ombremoon.spellbound.client.gui.guide.elements.special;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.IPageElement;
import com.ombremoon.spellbound.common.magic.acquisition.transfiguration.TransfigurationRitual;
import com.ombremoon.spellbound.main.Keys;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

public record TransfigurationRitualElement(ResourceKey<TransfigurationRitual> ritual, boolean leftPage) implements IPageElement {
    public static final MapCodec<TransfigurationRitualElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceKey.codec(Keys.RITUAL).fieldOf("ritual").forGetter(TransfigurationRitualElement::ritual),
            Codec.BOOL.fieldOf("leftPage").forGetter(TransfigurationRitualElement::leftPage)
    ).apply(inst, TransfigurationRitualElement::new));

    @Override
    public @NotNull MapCodec<? extends IPageElement> codec() {
        return CODEC;
    }
}
