package com.ombremoon.spellbound.common.magic.api.events;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.Nullable;

public class EffectApplicableEvent extends SpellEvent {
    private final MobEffectEvent.Applicable event;
    protected MobEffectEvent.Applicable.Result result = MobEffectEvent.Applicable.Result.DEFAULT;
    @Nullable
    private final Entity source;

    public EffectApplicableEvent(LivingEntity caster, MobEffectEvent.Applicable event) {
        super(caster, event);
        this.event = event;
        this.source = event.getEffectSource();
    }

    public MobEffectInstance getEffectInstance() {
        return this.event.getEffectInstance();
    }

    public void setResult(MobEffectEvent.Applicable.Result result) {
        this.event.setResult(result);
    }

    public MobEffectEvent.Applicable.Result getResult() {
        return this.result;
    }

    @Nullable
    public Entity getEffectSource() {
        return this.source;
    }

    @SuppressWarnings("deprecation") // Expected as the single call site for canBeAffected
    public boolean getApplicationResult() {
        if (this.event.getResult() == MobEffectEvent.Applicable.Result.APPLY) {
            return true;
        }
        return this.event.getResult() == MobEffectEvent.Applicable.Result.DEFAULT && this.caster.canBeAffected(this.getEffectInstance());
    }
}
