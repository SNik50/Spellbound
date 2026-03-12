package com.ombremoon.spellbound.common.magic.api;

import com.ombremoon.spellbound.common.magic.skills.Skill;

public interface RadialSpell {

    default Skill getChoice() {
        return ((AbstractSpell) this).choice;
    }

    default void setChoice(Skill choice) {
        ((AbstractSpell) this).choice = choice;
    }
}
