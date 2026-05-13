package com.ombremoon.spellbound.common.magic.api;

import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.skills.SkillProvider;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public interface RadialSpell {

    default SkillProvider getChoice() {
        return ((AbstractSpell) this).choice;
    }

    default void setChoice(SkillProvider choice) {
        ((AbstractSpell) this).choice = choice;
    }

    default boolean isMainChoice(SpellContext context) {
        AbstractSpell spell = (AbstractSpell) this;
        return this.getChoice() == spell.spellType().getRootSkill();
    }
}
