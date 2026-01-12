package com.ombremoon.spellbound.client.gui.guide.elements.extras;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record ItemRendererExtras(ResourceLocation pageScrap, float scale) implements IElementExtra{
    public static Codec<ItemRendererExtras> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", GuideBookManager.FIRST_PAGE).forGetter(ItemRendererExtras::pageScrap),
            Codec.FLOAT.optionalFieldOf("scale", 25f).forGetter(ItemRendererExtras::scale)
    ).apply(inst, ItemRendererExtras::new));

    public static ItemRendererExtras getDefault() {
        return new ItemRendererExtras(GuideBookManager.FIRST_PAGE, 25f);
    }
}
