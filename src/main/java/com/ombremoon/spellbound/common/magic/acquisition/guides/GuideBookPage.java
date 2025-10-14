package com.ombremoon.spellbound.common.magic.acquisition.guides;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.PageElement;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record GuideBookPage(ResourceLocation id, ResourceLocation insertAfter, List<PageElement> elements) {
    public static final Codec<GuideBookPage> CODEC = RecordCodecBuilder.<GuideBookPage>create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(GuideBookPage::id),
            ResourceLocation.CODEC.optionalFieldOf("insertAfter", CommonClass.customLocation("first_page_dont_use")).forGetter(GuideBookPage::insertAfter),
            PageElement.CODEC.listOf().optionalFieldOf("elements", new ArrayList<>()).forGetter(GuideBookPage::elements)
    ).apply(inst, GuideBookPage::new));

    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        for (PageElement element : elements) {
            element.render(Minecraft.getInstance().level, graphics, leftPos + 47, topPos + 36, mouseX, mouseY, partialTick);
        }
    }
}
