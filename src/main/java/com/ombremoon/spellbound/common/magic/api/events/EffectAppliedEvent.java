package com.ombremoon.spellbound.common.magic.api.events;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.Nullable;

public class EffectAppliedEvent extends SpellEvent {
    private final MobEffectEvent.Added event;
    @Nullable
    private final Entity source;

    public EffectAppliedEvent(LivingEntity sourceEntity, MobEffectEvent.Added event) {
        super(sourceEntity, event);
        this.event = event;
        this.source = event.getEffectSource();
    }

    public MobEffectInstance getEffectInstance() {
        return this.event.getEffectInstance();
    }

    @Nullable
    public Entity getEffectSource() {
        return this.source;
    }

}
