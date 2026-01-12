package com.ombremoon.spellbound.client.gui.guide.renderers;

import com.ombremoon.spellbound.client.gui.guide.elements.GuideEquipmentElement;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.EquipmentExtras;
import com.ombremoon.spellbound.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.joml.Quaternionf;

public class GuideEquipmentRenderer implements IPageElementRenderer<GuideEquipmentElement> {
    @Override
    public void render(GuideEquipmentElement element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, int tickCount) {
        ArmorStand stand = (ArmorStand) getData(element, "armor");

        if (stand == null) {
            stand = EntityType.ARMOR_STAND.create(Minecraft.getInstance().level);

            if (element.helmet().isPresent()) stand.setItemSlot(EquipmentSlot.HEAD, element.helmet().get());
            if (element.chestplate().isPresent()) stand.setItemSlot(EquipmentSlot.CHEST, element.chestplate().get());
            if (element.leggings().isPresent()) stand.setItemSlot(EquipmentSlot.LEGS, element.leggings().get());
            if (element.boots().isPresent()) stand.setItemSlot(EquipmentSlot.FEET, element.boots().get());
            if (element.offHand().isPresent()) stand.setItemSlot(EquipmentSlot.OFFHAND, element.offHand().get());
            if (element.mainHand().isPresent()) stand.setItemSlot(EquipmentSlot.MAINHAND, element.mainHand().get());

            EquipmentExtras extras = element.extras();
            stand.setHeadPose(extras.headRot().asVanilla());
            stand.setBodyPose(extras.bodyRot().asVanilla());
            stand.setLeftArmPose(extras.leftArmRot().asVanilla());
            stand.setRightArmPose(extras.rightArmRot().asVanilla());
            stand.setLeftLegPose(extras.leftLegRot().asVanilla());
            stand.setRightLegPose(extras.rightLegRot().asVanilla());

            saveData(element, "armor", stand);
        }

        RenderUtil.renderEntityInInventory(graphics,
                leftPos + element.position().xOffset(),
                topPos + element.position().yOffset(),
                element.extras().scale(),
                stand, true,
                new Quaternionf()
                        .rotateX((float) Math.toRadians(element.extras().standRot().x()))
                        .rotateY((float) Math.toRadians(element.extras().standRot().y()))
                        .rotateZ((float) Math.toRadians(element.extras().standRot().z())),
                false, mouseX, mouseY);
    }
}
