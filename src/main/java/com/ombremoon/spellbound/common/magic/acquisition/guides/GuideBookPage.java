package com.ombremoon.spellbound.common.magic.acquisition.guides;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.client.gui.guide.elements.IPageElement;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public record GuideBookPage(ResourceLocation id, ResourceLocation pageScrap, ResourceLocation insertAfter, List<IPageElement> elements) {
    public static final Codec<GuideBookPage> CODEC = RecordCodecBuilder.<GuideBookPage>create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(GuideBookPage::id),
            ResourceLocation.CODEC.optionalFieldOf("pageScrap", CommonClass.customLocation("default")).forGetter(GuideBookPage::pageScrap),
            ResourceLocation.CODEC.optionalFieldOf("insertAfter", CommonClass.customLocation("default")).forGetter(GuideBookPage::insertAfter),
            IPageElement.CODEC.listOf().optionalFieldOf("elements", new ArrayList<>()).forGetter(GuideBookPage::elements)
    ).apply(inst, GuideBookPage::new));

    public boolean isVisible(Player player) {
        return player.isCreative() || pageScrap.equals(GuideBookManager.FIRST_PAGE) || SpellUtil.hasScrap(player, pageScrap);
    }
}
