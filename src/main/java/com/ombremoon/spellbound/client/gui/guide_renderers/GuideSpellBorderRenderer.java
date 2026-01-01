package com.ombremoon.spellbound.client.gui.guide_renderers;

import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.GuideSpellBorderElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class GuideSpellBorderRenderer implements IPageElementRenderer<GuideSpellBorderElement> {
    @Override
    public void render(GuideSpellBorderElement element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, int tickCount) {
        topPos = topPos + element.topGap();

        graphics.blit(
                element.path(),
                leftPos - 7 + element.position().xOffset(),
                topPos + element.position().yOffset(),
                0, 0,
                16, 16, 16, 16
        );

        graphics.hLine(leftPos + 8, leftPos + 150, topPos + element.position().yOffset() + 8, -16777216);


        graphics.drawString(Minecraft.getInstance().font,
                "Mastery: Expert",
                leftPos + element.position().xOffset() + 30,
                topPos + 168, 0, false);

        graphics.blit(
                element.path(),
                leftPos + 135 + element.position().xOffset(),
                topPos + 170,
                0, 0,
                16, 16, 16, 16
        );

        graphics.hLine(leftPos + 12, leftPos + 135, topPos+178, -16777216);
    }
}
