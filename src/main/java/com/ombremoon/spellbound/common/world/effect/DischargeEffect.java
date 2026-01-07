package com.ombremoon.spellbound.common.world.effect;

import com.ombremoon.spellbound.common.init.SBEffects;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class DischargeEffect extends SBEffect {
    public DischargeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        for (int i = 0; i < 3; i++) {
            var bolt = EntityType.LIGHTNING_BOLT.create(entity.level());
            bolt.setPos(
                    entity.position().x + entity.getRandom().nextInt(-5, 5),
                    entity.position().y,
                    entity.position().z + entity.getRandom().nextInt(-5, 5));
            entity.level().addFreshEntity(bolt);
        }
        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
