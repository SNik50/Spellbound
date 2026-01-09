package com.ombremoon.spellbound.common.world.effect;

import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DischargeEffect extends SBEffect {
    public DischargeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        var handler = SpellUtil.getSpellHandler(livingEntity);
        handler.consumeMana((float) (handler.getMaxMana() * 0.04F));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
