package com.ombremoon.spellbound.common.magic.acquisition.guides.elements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.ElementPosition;
import com.ombremoon.spellbound.common.magic.acquisition.guides.elements.extras.EntityRendererExtras;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityTickList;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public record GuideEntityRenderer(ResourceLocation entityLoc, EntityRendererExtras extras, ElementPosition position) implements PageElement {
    public static final MapCodec<GuideEntityRenderer> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("entity").forGetter(GuideEntityRenderer::entityLoc),
            EntityRendererExtras.CODEC.optionalFieldOf("extras", EntityRendererExtras.getDefault()).forGetter(GuideEntityRenderer::extras),
            ElementPosition.CODEC.optionalFieldOf("position", ElementPosition.getDefault()).forGetter(GuideEntityRenderer::position)
    ).apply(inst, GuideEntityRenderer::new));

    @Override
    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY, float partialTick) {
        EntityType<?> entityType = Minecraft.getInstance().level.registryAccess().registry(Registries.ENTITY_TYPE).get().get(entityLoc);

        if (entityType == null) return;
        Entity entity = entityType.create(Minecraft.getInstance().level);
        if (!(entity instanceof LivingEntity livingEntity)) return;

        //Add obfuscated without scrap
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, leftPos + position.xOffset(), topPos + position.yOffset(), leftPos + (3*extras.scale()), topPos + (4*extras.scale()), extras.scale(), 0.25F, mouseX, mouseY, livingEntity);
    }

    @Override
    public @NotNull MapCodec<? extends PageElement> codec() {
        return CODEC;
    }
}
