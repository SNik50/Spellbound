package com.ombremoon.spellbound.common.world.entity.living;

import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.world.entity.SBLivingEntity;
import com.ombremoon.spellbound.common.world.entity.SmartSpellEntity;
import com.ombremoon.spellbound.common.world.spell.deception.ShadowbondSpell;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public class LivingShadow extends SBLivingEntity {
    private static final ResourceLocation BLINDING_MIRAGE = CommonClass.customLocation("blinding_mirage");
    public LivingShadow(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public int getStartTick() {
        return 0;
    }

    @Override
    public int getEndTick() {
        return 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount >= 200) discard();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            SpellType<?> spell = SBSpells.REGISTRY.get(this.getData(SBData.SPELL_TYPE));
            Entity entity = SpellUtil.getOwner(this);
            Entity entity1 = source.getEntity();
            if (spell != null && entity instanceof LivingEntity owner && entity1 instanceof LivingEntity attacker && !attacker.is(owner)) {
                var skills = SpellUtil.getSkills(owner);
                if (spell.is(SBSpells.FLICKER)) {
                    if (skills.hasSkill(SBSkills.BLINDING_MIRAGE)) {
                        var handler = SpellUtil.getSpellHandler(attacker);
                        SkillBuff<?> skillBuff = new SkillBuff<>(
                                SBSkills.BLINDING_MIRAGE.value(),
                                BLINDING_MIRAGE,
                                BuffCategory.HARMFUL,
                                SkillBuff.MOB_EFFECT,
                                new MobEffectInstance(MobEffects.BLINDNESS, 40));
                        handler.addSkillBuff(skillBuff, attacker, 40);
                    }
                }
            }
        }

        return super.hurt(source, amount);
    }

    public static AttributeSupplier.Builder createLivingShadowAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SmartSpellEntity<ShadowbondSpell>>> getSensors() {
        return ObjectArrayList.of();
    }
}
