package com.ombremoon.spellbound.client.gui.guide.renderers;

import com.ombremoon.spellbound.client.gui.guide.elements.GuideArmorElement;
import com.ombremoon.spellbound.client.gui.guide.elements.extras.EquipmentExtras;
import com.ombremoon.spellbound.client.particle.EffectBuilder;
import com.ombremoon.spellbound.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;
import org.joml.Quaternionf;

public class GuideArmorRenderer implements IPageElementRenderer<GuideArmorElement> {
    @Override
    public void render(GuideArmorElement element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, int tickCount) {
        ArmorStand stand = (ArmorStand) getData(element, "armor");

        if (stand == null) {
            stand = EntityType.ARMOR_STAND.create(Minecraft.getInstance().level);

            stand.setItemSlot(EquipmentSlot.HEAD, element.helmet());
            stand.setItemSlot(EquipmentSlot.CHEST, element.chestplate());
            stand.setItemSlot(EquipmentSlot.LEGS, element.leggings());
            stand.setItemSlot(EquipmentSlot.FEET, element.boots());
            stand.setItemSlot(EquipmentSlot.OFFHAND, element.offHand());
            stand.setItemSlot(EquipmentSlot.MAINHAND, element.mainHand());

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
