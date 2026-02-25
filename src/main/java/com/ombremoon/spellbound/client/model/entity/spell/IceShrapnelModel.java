package com.ombremoon.spellbound.client.model.entity.spell;

import com.ombremoon.spellbound.common.world.entity.spell.IceBolt;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IceShrapnelModel extends GeoModel<IceBolt> {

    @Override
    public ResourceLocation getModelResource(IceBolt animatable) {
        return CommonClass.customLocation("geo/entity/ice_shrapnel/ice_shrapnel.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IceBolt animatable) {
        return CommonClass.customLocation("textures/entity/ice_shrapnel/ice_shrapnel.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IceBolt animatable) {
        return CommonClass.customLocation("animations/entity/ice_shrapnel/ice_shrapnel.animation.json");
    }
}
