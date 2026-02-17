package com.ombremoon.spellbound.common.world.spell.deception;

import com.ombremoon.spellbound.common.init.SBEntities;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.effects.MagicEffect;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CursedRuneSpell extends AnimatedSpell {
    private static Builder<CursedRuneSpell> createCursedRuneBuilder() {
        return createSimpleSpellBuilder(CursedRuneSpell.class)
                .duration(300)
                .manaCost(10)
                .baseDamage(2)
                .castCondition((context, spell) -> {
                    Vec3 pos = spell.getSpawnVec();
                    if (pos != null) {
                        spell.spawnPos = pos;
                        return true;
                    }

                    return false;
                });
    }
    private Vec3 spawnPos;

    public CursedRuneSpell() {
        super(SBSpells.CURSED_RUNE.get(), createCursedRuneBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {
        LivingEntity caster = context.getCaster();
        Level level = context.getLevel();
        if (!level.isClientSide) {
            MagicEffect effect = null;
            if (context.isChoice(SBSkills.MAGE_WRECK)) {
                
            } else if (context.isChoice(SBSkills.DISARMING_CURSE)) {
                
            } else if (context.isChoice(SBSkills.MIRROR_CURSE)) {

            } else if (context.isChoice(SBSkills.CURSE_OF_PAIN)) {

            } else if (context.isChoice(SBSkills.CURSE_OF_DROUGHT)) {

            } else if (context.isChoice(SBSkills.VANISHING_CURSE)) {

            } else if (context.isChoice(SBSkills.CURSE_OF_WEAKNESS)) {

            } else if (context.isChoice(SBSkills.CURSE_OF_SUSCEPTIBILITY)) {

            } else if (context.isChoice(SBSkills.TANGLEFOOT_CURSE)) {

            }

            this.summonEntity(context, SBEntities.CURSED_RUNE.get(), this.spawnPos, cursedRune -> cursedRune.setRuneEffect(effect));
        }
    }

    @Override
    protected void onSpellStop(SpellContext context) {

    }
}
