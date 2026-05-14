package com.ombremoon.spellbound.common.world.spell.deception;

import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;
import com.ombremoon.spellbound.common.magic.api.SpellType;

public class ShadowVeilSpell extends AnimatedSpell {

    private static Builder<ShadowVeilSpell> createShadowVeilSpell() {
        return createSimpleSpellBuilder(ShadowVeilSpell.class)
                .manaCost(15)
                .duration(10);
    }

    public ShadowVeilSpell() {
        super(SBSpells.SHADOW_VEIL.get(), createShadowVeilSpell());
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
