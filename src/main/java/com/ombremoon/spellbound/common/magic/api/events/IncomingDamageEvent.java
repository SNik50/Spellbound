package com.ombremoon.spellbound.common.magic.api.events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.damagesource.IReductionFunction;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class IncomingDamageEvent extends SpellEvent {
    private final LivingIncomingDamageEvent event;

    public IncomingDamageEvent(LivingEntity caster, LivingIncomingDamageEvent event) {
        super(caster, event);
        this.event = event;
    }

    public DamageContainer getContainer() {
        return this.event.getContainer();
    }

    public DamageSource getSource() {
        return this.event.getSource();
    }

    public float getNewDamage() {
        return this.event.getAmount();
    }

    public float getOriginalDamage() {
        return this.event.getOriginalAmount();
    }

    public void setNewDamage(float newDamage) {
            this.event.setAmount(newDamage);
        }

    public void addReductionModifier(DamageContainer.Reduction type, IReductionFunction reductionFunc) {
        this.event.addReductionModifier(type, reductionFunc);
    }

    public void setInvulnerabilityTicks(int ticks) {
        this.event.setInvulnerabilityTicks(ticks);
    }

}
