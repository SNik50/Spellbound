package com.ombremoon.spellbound.common.world.spell.ruin.fire;

import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;

public class FireballSpell extends AnimatedSpell {

    public static Builder<FireballSpell> createFireballBuilder() {
        return createSimpleSpellBuilder(FireballSpell.class)
                .manaCost(30);
    }

    public FireballSpell() {
        super(SBSpells.FIREBALL.get(), createFireballBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {

    }

    @Override
    protected void onSpellStop(SpellContext context) {

    }
}
