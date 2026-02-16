package com.ombremoon.spellbound.common.world.spell.ruin.fire;

import com.ombremoon.spellbound.common.init.SBEntities;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;
import com.ombremoon.spellbound.common.magic.api.ChargeableSpell;
import com.ombremoon.spellbound.common.magic.api.RadialSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FireballSpell extends AnimatedSpell implements RadialSpell, ChargeableSpell {

    public static Builder<FireballSpell> createFireballBuilder() {
        return createSimpleSpellBuilder(FireballSpell.class)
                .manaCost(30)
                .castCondition((context, fireballSpell) -> {
                    if (context.isChoice(SBSkills.HOMING_MISSILE)) {
                        return context.hasSkill(SBSkills.AUTO_TARGETING) || context.getTarget() instanceof LivingEntity;
                    }

                    return true;
                });
    }

    public FireballSpell() {
        super(SBSpells.FIREBALL.get(), createFireballBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            if (context.isChoice(SBSkills.RAPID_FIRE)) {

            } else {
                int count = context.isChoice(SBSkills.VOLATILE_CLUSTER) ? 3 : 1;
                for (int i = 0; i < count; i++) {
                    this.shootProjectile(context, SBEntities.FIREBALL.get(), 1.5F, 1.0F);
                }
            }
        }
    }

    @Override
    protected void onSpellStop(SpellContext context) {

    }

    @Override
    public int maxCharges(SpellContext context) {
        return 3;
    }

    @Override
    public boolean canCharge(SpellContext context) {
        return context.isChoice(SBSkills.CHARGED_BLAST);
    }
}
