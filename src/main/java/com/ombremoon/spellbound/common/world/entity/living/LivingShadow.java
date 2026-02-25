package com.ombremoon.spellbound.common.world.entity.living;

import com.ombremoon.spellbound.common.init.*;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.world.entity.SBLivingEntity;
import com.ombremoon.spellbound.common.world.entity.SmartSpellEntity;
import com.ombremoon.spellbound.common.world.spell.deception.FlickerSpell;
import com.ombremoon.spellbound.common.world.spell.deception.ShadowbondSpell;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;
import java.util.List;

public class LivingShadow extends SBLivingEntity {
    private static final ResourceLocation BLINDING_MIRAGE = CommonClass.customLocation("blinding_mirage");
    private static final EntityDataAccessor<Boolean> SET_TARGET = SynchedEntityData.defineId(LivingShadow.class, EntityDataSerializers.BOOLEAN);

    public LivingShadow(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SET_TARGET, false);
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

        if (!this.level().isClientSide) {
            //You'd expect one to not have to do this, yet here we are smh
            this.setOnGround(true);
            SpellType<?> spell = SBSpells.REGISTRY.get(this.getData(SBData.SPELL_TYPE));
            Entity entity = SpellUtil.getOwner(this);
            if (spell != null && entity instanceof LivingEntity owner) {
                var handler = SpellUtil.getSpellHandler(owner);
                var skills = SpellUtil.getSkills(owner);
                if (spell.is(SBSpells.FLICKER)) {
                    if (skills.hasSkill(SBSkills.PHANTOM_LURE)) {
                        var list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0D), target -> SpellUtil.CAN_ATTACK_ENTITY.test(owner, target));
                        for (LivingEntity target : list) {
                            if (!target.hasEffect(SBEffects.TAUNT)) {
                                var shadowHandler = SpellUtil.getSpellHandler(this);
                                shadowHandler.applyTauntEffect(target, 40);
                            }
                        }
                    }

                    FlickerSpell flickerSpell = handler.getSpell(SBSpells.FLICKER.get());
                    if (this.tickCount == 1) {
                        if (this.hasSetTarget() && flickerSpell != null) {
                            BrainUtils.setMemory(this, MemoryModuleType.WALK_TARGET, new WalkTarget(flickerSpell.getTeleportLocation(), 1.5F, 1));
                        } else if (skills.hasSkill(SBSkills.SHADOW_FEINT)) {
                            Vec3 targetPos = LandRandomPos.getPos(this, 10, 0);
                            if (targetPos != null) {
                                BrainUtils.setMemory(this, MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 1.5F, 1));
                            }
                        }
                    }
                }
            }

            if (this.tickCount >= 100) {
                discard();
            }
        }
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
                    var handler = SpellUtil.getSpellHandler(attacker);
                    if (skills.hasSkill(SBSkills.BLINDING_MIRAGE)) {
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

    public boolean hasSetTarget() {
        return this.entityData.get(SET_TARGET);
    }

    public void setSetTarget(boolean flag) {
        this.entityData.set(SET_TARGET, flag);
    }

    public static AttributeSupplier.Builder createLivingShadowAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SmartSpellEntity<ShadowbondSpell>>> getSensors() {
        return ObjectArrayList.of();
    }

    @Override
    public BrainActivityGroup<? extends SBLivingEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new WalkOrRunToWalkTarget<>()
        );
    }

    /*@Override
    public BrainActivityGroup<? extends SBLivingEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new SetRandomWalkTarget<>()
        );
    }*/
}
