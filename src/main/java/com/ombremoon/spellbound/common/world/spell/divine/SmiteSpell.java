package com.ombremoon.spellbound.common.world.spell.divine;

import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.Imbuement;
import com.ombremoon.spellbound.common.magic.api.ImbuementSpell;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SpellEventListener;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SmiteSpell extends ImbuementSpell {
    private static final ResourceLocation GOLDEN_PARRY = CommonClass.customLocation("golden_parry");
    private long parryTick;

    public static Builder<SmiteSpell> createSmiteBuilder() {
        return createImbuementSpellBuilder(SmiteSpell.class)
                .duration(1200)
                .negativeScaling((context, smiteSpell) -> smiteSpell.isChoice(SBSkills.BLACK_BLADE))
                /*.imbuementEffect((context, smiteSpell) -> EffectData.Entity.of(
                                CommonClass.customLocation("shadow_mist"),
                                context.getCaster().getId(),
                                EntityEffectExecutor.AutoRotate.NONE)
                        .setOffset(0, -1.5, 0))*/;
    }

    public SmiteSpell() {
        super(SBSpells.SMITE.get(), createSmiteBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {
        super.onSpellStart(context);
        LivingEntity caster = context.getCaster();
        Level level = context.getLevel();
        if (!level.isClientSide) {
            if (context.hasSkill(SBSkills.GOLDEN_PARRY)) {
                this.addEventBuff(
                        caster,
                        SBSkills.GOLDEN_PARRY,
                        BuffCategory.BENEFICIAL,
                        SpellEventListener.Events.INCOMING_DAMAGE,
                        GOLDEN_PARRY,
                        incomingDamage -> {
                            Entity source = incomingDamage.getSource().getEntity();
                            if (source instanceof LivingEntity living && Math.abs(this.parryTick - living.getData(SBData.ATTACK_START)) < 20) {
                                log(living.getData(SBData.ATTACK_START));
                                incomingDamage.cancelEvent();
                            }
                        }
                );
            }
        }
    }

    @Override
    protected void onUseImbuement(SpellContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            if (context.isChoice(SBSkills.GOLDEN_PARRY)) {
                this.parryTick = level.getGameTime();
//                log(this.parryTick);
            }
        }
    }

    @Override
    protected Imbuement createImbuement(SpellContext context) {
        return context.isChoice(SBSkills.BLACK_BLADE) ? new Imbuement(this.spellType(), CommonClass.customLocation("smite_black_blade")) : super.createImbuement(context);
    }

    @Override
    public boolean isMainChoice(SpellContext context) {
        return super.isMainChoice(context) || context.isChoice(SBSkills.BLACK_BLADE);
    }
}
