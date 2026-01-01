package com.ombremoon.spellbound.client.gui.guide_renderers;

import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.GuideItemElement;
import com.ombremoon.spellbound.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class GuideItemRenderer implements IPageElementRenderer<GuideItemElement> {
    ItemEntity entity;

    @Override
    public void render(GuideItemElement element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, int tickCount) {
        GuideRecipeRenderer.SBGhostItem ghostItem = new GuideRecipeRenderer.SBGhostItem(GuideUtil.buildIngredient(element.items()), 0, 0);

        ItemStack stack = ghostItem.getItem(tickCount);
        ItemEntity entity = EntityType.ITEM.create(Minecraft.getInstance().level);
        entity.setItem(stack);

        entity.tickCount = tickCount;

        RenderUtil.renderEntityInInventory(
                graphics,
                leftPos + element.position().xOffset()+100,
                topPos + element.position().yOffset()+100,
                element.extras().scale(),
                entity,
                isVisible(element.extras().pageScrap()),
                new Quaternionf(),
                false,
                0,0,
                true
        );
    }

}
