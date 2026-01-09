package com.ombremoon.spellbound.common.world.effect;

import com.ombremoon.spellbound.common.init.SBDamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;

import java.util.Optional;

public class CombustEffect extends SBEffect {
    public CombustEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        livingEntity.level().explode(null,
                livingEntity.level().damageSources().source(SBDamageTypes.RUIN_FIRE),
                new SimpleExplosionDamageCalculator(false, true, Optional.of(0f), Optional.empty()) {
                    @Override
                    public float getEntityDamageAmount(Explosion explosion, Entity entity) {
                        return Math.min(super.getEntityDamageAmount(explosion, entity), 10);
                    }
                },
                livingEntity.getX(),
                livingEntity.getY()+1,
                livingEntity.getZ(),
                3,
                true,
                Level.ExplosionInteraction.MOB);
    }
}
