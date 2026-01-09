package com.ombremoon.spellbound.common.world.effect;

import com.ombremoon.spellbound.common.magic.api.buff.SpellModifier;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SBEffect extends MobEffect {
    private List<SpellModifier> spellModifiers;

    public SBEffect(MobEffectCategory category, int color) {
        super(category, color);
        spellModifiers = new ArrayList<>();
    }

    public void onEffectRemoved(LivingEntity livingEntity, int amplifier) {
    }

    @Override
    public int getBlendDurationTicks() {
        Constants.LOG.info("{}", super.getBlendDurationTicks());
        return super.getBlendDurationTicks();
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public SBEffect addSpellModifiers(SpellModifier modifier) {
        this.spellModifiers.add(modifier);
        return this;
    }

    public List<SpellModifier> getSpellModifiers() {
        return this.spellModifiers;
    }
}
