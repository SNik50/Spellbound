package com.ombremoon.spellbound.client.gui.guide_renderers;

import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.GuideItemRenderer;
import com.ombremoon.spellbound.datagen.provider.guide_builders.PageBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

public class GuideItemRendererRenderer implements IPageElementRenderer<GuideItemRenderer> {
    ItemEntity entity;

    @Override
    public void render(GuideItemRenderer element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        Item item = BuiltInRegistries.ITEM.get(element.itemLoc());
        if (item.equals(Items.AIR)) return;

        if (entity == null) {
            ItemStack stack = item.getDefaultInstance();
            this.entity = EntityType.ITEM.create(Minecraft.getInstance().level);
            this.entity.setItem(stack);
        }

        entity.tickCount++;

        GuideEntityRendererRenderer.renderEntityInInventory(
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
