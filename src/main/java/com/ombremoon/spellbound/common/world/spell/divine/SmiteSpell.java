package com.ombremoon.spellbound.common.world.spell.divine;

import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.ombremoon.spellbound.client.photon.EffectBuilder;
import com.ombremoon.spellbound.client.photon.converter.EffectData;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.ImbuementSpell;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SpellEventListener;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SmiteSpell extends ImbuementSpell {
    private static final ResourceLocation SACRED_PARRY = CommonClass.customLocation("sacred_parry");
    public static Builder<SmiteSpell> createSmiteBuilder() {
        return createImbuementSpellBuilder(SmiteSpell.class)
                .duration(200)
                .negativeScaling((context, smiteSpell) -> smiteSpell.isChoice(SBSkills.BLACK_BLADE))
                .imbuementEffect((context, smiteSpell) -> EffectData.Entity.of(
                                CommonClass.customLocation("shadow_mist"),
                                context.getCaster().getId(),
                                EntityEffectExecutor.AutoRotate.NONE)
                        .setOffset(0, -1.5, 0));
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
            if (context.hasSkill(SBSkills.SACRED_PARRY)) {
                this.addEventBuff(
                        caster,
                        SBSkills.SACRED_PARRY,
                        BuffCategory.BENEFICIAL,
                        SpellEventListener.Events.PRE_DAMAGE,
                        SACRED_PARRY,
                        pre -> {

                        }
                );
            }
        }
    }

    @Override
    protected void onUseImbuement(SpellContext context) {
        if (context.isChoice(SBSkills.SACRED_PARRY)) {

        }
    }
}
