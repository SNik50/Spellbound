package com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;

public record StaticItemExtras(ResourceLocation pageScrap, float scale) implements IElementExtra {
    public static final Codec<StaticItemExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", CommonClass.customLocation("default")).forGetter(StaticItemExtras::pageScrap),
            Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(StaticItemExtras::scale)
    ).apply(inst, StaticItemExtras::new));

    public static StaticItemExtras getDefault() {
        return new StaticItemExtras(CommonClass.customLocation("default"), 1f);
    }


}
