package com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;

public record EntityRendererExtras(ResourceLocation pageScrap, int scale) implements IElementExtra {
    public static final Codec<EntityRendererExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", CommonClass.customLocation("default")).forGetter(EntityRendererExtras::pageScrap),
            Codec.INT.optionalFieldOf("scale", 25).forGetter(EntityRendererExtras::scale)
    ).apply(inst, EntityRendererExtras::new));

    public static EntityRendererExtras getDefault() {
        return new EntityRendererExtras(CommonClass.customLocation("default"), 25);
    }
}
