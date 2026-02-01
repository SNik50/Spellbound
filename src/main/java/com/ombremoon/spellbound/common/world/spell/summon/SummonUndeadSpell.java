package com.ombremoon.spellbound.common.world.spell.summon;

import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.*;
import com.ombremoon.spellbound.common.magic.sync.SpellDataKey;
import com.ombremoon.spellbound.common.magic.sync.SyncedSpellData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SummonUndeadSpell extends SummonSpell implements ChargeableSpell, RadialSpell {
    public static Builder<SummonUndeadSpell> createSummonBuilder() {
        return createSummonBuilder(SummonUndeadSpell.class)
                .manaCost(10)
                .duration(2400)
                .isChoice();
    }

    public SummonUndeadSpell() {
        super(SBSpells.SUMMON_UNDEAD.get(), createSummonBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    public void onSpellDataUpdated(List<SyncedSpellData.DataValue<?>> newData) {
        super.onSpellDataUpdated(newData);
        log(newData.getFirst().value());
    }

    @Override
    protected void onSpellStart(SpellContext context) {
        super.onSpellStart(context);
        LivingEntity caster = context.getCaster();
        int charges = this.getCharges() + 1;
        if (!context.getLevel().isClientSide) {
            EntityType<?> undead = EntityType.ZOMBIE;
            if (context.isChoice(SBSkills.SUMMON_HUSK)) {
                undead = EntityType.HUSK;
            } else if (context.isChoice(SBSkills.SUMMON_SKELETON)) {
                undead = EntityType.SKELETON;
            } else if (context.isChoice(SBSkills.SUMMON_PHANTOM)) {
                undead = EntityType.PHANTOM;
            } else if (context.isChoice(SBSkills.SUMMON_DROWNED)) {
                undead = EntityType.DROWNED;
            }

            float radius = 1.5F;
            Vec3 origin = caster.position();
            float yaw = caster.getYRot();
            for (int i = 0; i < charges; i++) {
                Vec3 spawnOffset = this.getSurroundingSpawnPosition(origin, yaw, radius, i, charges);
                this.summonEntity(context, undead, spawnOffset);
            }
        }
    }

    @Override
    public int maxCharges(SpellContext context) {
        int defaultMax = context.getSpellLevel() + 1;
        int currentSummonSize = this.getSummonSize(context);
        return Math.max(0, defaultMax - currentSummonSize);
    }

    @Override
    public boolean canCharge(SpellContext context) {
        return true;
    }
}
