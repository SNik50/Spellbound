package com.ombremoon.spellbound.client.model.entity.projectile;

import com.ombremoon.spellbound.common.world.entity.spell.StormstrikeBolt;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;
import net.tslat.smartbrainlib.util.RandomUtil;
import software.bernie.geckolib.model.GeoModel;

public class StormstrikeBoltModel extends GeoModel<StormstrikeBolt> {
    @Override
    public ResourceLocation getModelResource(StormstrikeBolt animatable) {
        return RandomUtil.fiftyFifty()
                ? CommonClass.customLocation("geo/entity/stormstrike_bolt/stormstrike_bolt1.geo.json")
                : CommonClass.customLocation("geo/entity/stormstrike_bolt/stormstrike_bolt2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StormstrikeBolt animatable) {
        return CommonClass.customLocation("textures/entity/stormstrike_bolt/stormstrike_bolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(StormstrikeBolt animatable) {
        return CommonClass.customLocation("animations/entity/stormstrike_bolt/stormstrike_bolt.animation.json");
    }
}
