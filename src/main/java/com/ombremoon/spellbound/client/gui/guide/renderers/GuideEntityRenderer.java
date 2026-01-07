package com.ombremoon.spellbound.client.gui.guide.renderers;

import com.ombremoon.spellbound.client.gui.guide.elements.GuideEntityElement;
import com.ombremoon.spellbound.common.init.SBEntities;
import com.ombremoon.spellbound.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.joml.Quaternionf;
import software.bernie.geckolib.animatable.GeoEntity;

public class GuideEntityRenderer implements IPageElementRenderer<GuideEntityElement> {

    @Override
    public void render(GuideEntityElement element, GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick, int tickCount) {
        Entity entity = (Entity) getData(element, "entity");
        Integer selectedEntity = (Integer) getData(element, "entityIndex");
        int entityIndex = Mth.floor(tickCount / 30.0F) % element.entityLoc().size();

        if (entity == null || selectedEntity == null || selectedEntity != entityIndex) {
            Registry<EntityType<?>> registry = BuiltInRegistries.ENTITY_TYPE;
            EntityType<?> entityType = registry.get(element.entityLoc().get(entityIndex));

            if (entityType == null) {
                LOGGER.warn("Entity could not be found {}", element.entityLoc());
                return;
            }

            entity = entityType.create(Minecraft.getInstance().level);
            saveData(element, "entity", entity);
            saveData(element, "entityIndex", entityIndex);
        }

        RenderUtil.renderEntityInInventory(graphics,
                leftPos + element.position().xOffset(),
                topPos + element.position().yOffset(),
                element.extras().scale(),
                entity, isVisible(element.extras().pageScrap()),
                new Quaternionf()
                        .rotateX((float) Math.toRadians(element.extras().xRot()))
                        .rotateY((float) Math.toRadians(element.extras().yRot()))
                        .rotateZ((float) Math.toRadians(element.extras().zRot())),
                element.extras().followMouse(), mouseX, mouseY);
    }


}
