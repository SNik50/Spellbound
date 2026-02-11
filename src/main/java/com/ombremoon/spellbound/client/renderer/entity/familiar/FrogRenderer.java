package com.ombremoon.spellbound.client.renderer.entity.familiar;

import com.ombremoon.spellbound.client.renderer.entity.SBModelLayerLocs;
import com.ombremoon.spellbound.common.world.entity.living.familiars.FrogEntity;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class FrogRenderer<T extends FrogEntity> extends MobRenderer<T, FrogModel<T>> {

    public FrogRenderer(EntityRendererProvider.Context context) {
        super(context, new FrogModel<>(context.bakeLayer(SBModelLayerLocs.FROG)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(FrogEntity frogEntity) {
        return CommonClass.customLocation("textures/entity/familiar/frog/frog.png");
    }

    @Override
    protected @Nullable RenderType getRenderType(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.itemEntityTranslucentCull(getTextureLocation(livingEntity));
    }
}
