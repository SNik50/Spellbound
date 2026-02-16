package com.ombremoon.spellbound.common.world.spell.ruin.fire;

import com.ombremoon.spellbound.common.init.SBEntities;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;
import com.ombremoon.spellbound.common.magic.api.ChargeableSpell;
import com.ombremoon.spellbound.common.magic.api.RadialSpell;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.world.entity.EntitySelector;
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
        LivingEntity caster = context.getCaster();
        if (!level.isClientSide) {
            if (context.isChoice(SBSkills.RAPID_FIRE)) {

            } else {
                int count = context.isChoice(SBSkills.VOLATILE_CLUSTER) ? 3 : 1;
                for (int i = 0; i < count; i++) {
                    float yAngle = getFireballAngle(caster, i, count);
                    this.shootProjectile(context, SBEntities.FIREBALL.get(), caster.getXRot(), yAngle, 1.5F, 1.0F, projectile -> {
                        if (context.isChoice(SBSkills.HOMING_MISSILE)) {
                            if (context.getTarget() instanceof LivingEntity target) {
                                projectile.setHomingTarget(target);
                            } else if (context.hasSkill(SBSkills.AUTO_TARGETING)) {
                                var list = level.getEntitiesOfClass(LivingEntity.class, projectile.getBoundingBox().inflate(10), livingEntity -> SpellUtil.CAN_ATTACK_ENTITY.test(caster, livingEntity));
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onSpellTick(SpellContext context) {
        super.onSpellTick(context);
        Level level = context.getLevel();
        LivingEntity caster = context.getCaster();
        if (!level.isClientSide) {
            if (context.isChoice(SBSkills.RAPID_FIRE)) {

            } else {
                int count = context.isChoice(SBSkills.VOLATILE_CLUSTER) ? 3 : 1;
                for (int i = 0; i < count; i++) {
                    float yAngle = getFireballAngle(caster, i, count);
                    this.shootProjectile(context, SBEntities.FIREBALL.get(), caster.getXRot(), yAngle, 1.5F, 1.0F, projectile -> {});
                }
            }
        }
    }

    private float getFireballAngle(LivingEntity caster, int i, int count) {
        float yRot = caster.getYRot();
        float spread = 25.0F;
        float totalSpread = spread * (count - 1);
        float startOffset = -totalSpread / 2.0F;
        return yRot + startOffset + (i * spread);
    }

    @Override
    protected void onSpellStop(SpellContext context) {

    }

    @Override
    public int maxCharges(SpellContext context) {
        return 3;
    }

    @Override
    protected int getDuration(SpellContext context) {
        return context.isChoice(SBSkills.RAPID_FIRE) ? 31 : super.getDuration(context);
    }

    @Override
    public boolean canCharge(SpellContext context) {
        return context.isChoice(SBSkills.CHARGED_BLAST) || context.isChoice(SBSkills.VOLATILE_CLUSTER);
    }

    @Override
    public boolean shouldRender(SpellContext context) {
        return false;
    }
}
