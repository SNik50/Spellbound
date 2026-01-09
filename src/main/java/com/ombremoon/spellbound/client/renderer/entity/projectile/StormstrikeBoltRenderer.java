package com.ombremoon.spellbound.client.renderer.entity.projectile;

import com.ombremoon.spellbound.client.model.entity.projectile.StormstrikeBoltModel;
import com.ombremoon.spellbound.client.renderer.types.EmissiveSpellProjectileRenderer;
import com.ombremoon.spellbound.common.world.entity.spell.StormstrikeBolt;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StormstrikeBoltRenderer extends EmissiveSpellProjectileRenderer<StormstrikeBolt> {
    public StormstrikeBoltRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new StormstrikeBoltModel());
    }
}
