package com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record GuideImageExtras(boolean enableCorners) implements IElementExtra {
    public static final Codec<GuideImageExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.BOOL.optionalFieldOf("enableCorners", true).forGetter(GuideImageExtras::enableCorners)
    ).apply(inst, GuideImageExtras::new));

    public static GuideImageExtras getDefault() {
        return new GuideImageExtras(true);
    }
}
