package com.ombremoon.spellbound.common.magic.api.events;

import com.ombremoon.spellbound.common.events.custom.SpellCastEvent;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class CastSpellEvent extends SpellEvent {
    private final SpellCastEvent event;
    private final AbstractSpell spell;
    private final SpellContext context;

    public CastSpellEvent(LivingEntity caster, SpellCastEvent event) {
        super(caster, event);
        this.event = event;
        this.spell = event.getSpell();
        this.context = event.getContext();
    }

    public AbstractSpell getSpell() {
        return this.spell;
    }

    public SpellContext getContext() {
        return this.context;
    }
}
