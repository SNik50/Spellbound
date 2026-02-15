package com.ombremoon.spellbound.common.world.spell.deception;

import com.ombremoon.spellbound.common.init.*;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AnimatedSpell;
import com.ombremoon.spellbound.common.magic.api.RadialSpell;
import com.ombremoon.spellbound.common.magic.api.SpellAnimation;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.magic.sync.SpellDataKey;
import com.ombremoon.spellbound.common.magic.sync.SyncedSpellData;
import com.ombremoon.spellbound.common.world.entity.living.LivingShadow;
import com.ombremoon.spellbound.common.world.entity.spell.SolarRay;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.UnknownNullability;

public class FlickerSpell extends AnimatedSpell implements RadialSpell {
    private static final ResourceLocation STEP_INTO_SHADOW = CommonClass.customLocation("step_into_shadow");
    private static final ResourceLocation LOOK_OVER_HERE = CommonClass.customLocation("look_over_here");
    private static final SpellDataKey<Integer> LIVING_SHADOW = SyncedSpellData.registerDataKey(FlickerSpell.class, SBDataTypes.INT.get());
    private Vec3 teleportLocation;

    private static Builder<FlickerSpell> createFlickerBuilder() {
        return createSimpleSpellBuilder(FlickerSpell.class)
                .duration(5)
                .manaCost(27)
                .castTime(5)
                .castAnimation((context, spell) -> {
                    boolean flag = !context.hasSkill(SBSkills.SWIFT_SHADOWS);
                    String castPrefix = flag ? "" : "walking_";
                    return new SpellAnimation(castPrefix + "instant_cast", SpellAnimation.Type.CAST, flag);
                })
                .castCondition((context, flickerSpell) -> {
                    double range = SpellUtil.getCastRange(context.getCaster());
                    if (!context.hasSkill(SBSkills.DISTANT_FLICKER))
                        range /= 2;

                    Vec3 vec3 = flickerSpell.findTeleportLocation(context.getLevel(), context.getCaster(), (float) range);
                    if (vec3 != null) {
                        flickerSpell.teleportLocation = vec3;
                        return true;
                    }

                    return false;
                })
                .fullRecast(true);
    }

    public FlickerSpell() {
        super(SBSpells.FLICKER.get(), createFlickerBuilder());
    }

    @Override
    protected void defineSpellData(SyncedSpellData.Builder builder) {
        super.defineSpellData(builder);
        builder.define(LIVING_SHADOW, 0);
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {
        LivingEntity caster = context.getCaster();
        Level level = context.getLevel();
        if (!level.isClientSide) {
            if (context.hasSkill(SBSkills.HALL_OF_MIRRORS) && context.isRecast())
                return;

            if (context.isChoice(SBSkills.LOOK_OVER_HERE)) {
                this.summonEntity(context, SBEntities.LIVING_SHADOW.get(), caster.position(), livingShadow -> {
                    this.setLivingShadow(livingShadow.getId());
                    livingShadow.setSetTarget(true);
                });
                if (!caster.hasEffect(SBEffects.MAGI_INVISIBILITY)) {
                    this.addSkillBuff(
                            caster,
                            SBSkills.LOOK_OVER_HERE,
                            LOOK_OVER_HERE,
                            BuffCategory.BENEFICIAL,
                            SkillBuff.MOB_EFFECT,
                            new MobEffectInstance(SBEffects.MAGI_INVISIBILITY, 80),
                            80
                    );
                }
            } else {
                Vec3 vec3 = this.teleportLocation;
                if (context.hasSkill(SBSkills.CONFUSION)) {
                    this.summonEntity(context, SBEntities.LIVING_SHADOW.get(), caster.position(), livingShadow -> this.setLivingShadow(livingShadow.getId()));
                }

                caster.teleportTo(vec3.x, vec3.y, vec3.z);
                if (context.hasSkill(SBSkills.STEP_INTO_SHADOW)) {
                    this.addSkillBuff(
                            caster,
                            SBSkills.STEP_INTO_SHADOW,
                            STEP_INTO_SHADOW,
                            BuffCategory.BENEFICIAL,
                            SkillBuff.MOB_EFFECT,
                            new MobEffectInstance(SBEffects.MAGI_INVISIBILITY, 80),
                            80
                    );
                }

                if (!context.hasSkill(SBSkills.SILENT_STEP))
                    level.playSound(null, caster.xo, caster.yo, caster.zo, SoundEvents.ENDERMAN_TELEPORT, caster.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    @Override
    protected void onSpellRecast(SpellContext context) {
        Level level = context.getLevel();
        LivingEntity caster = context.getCaster();
        if (!level.isClientSide) {
            LivingShadow livingShadow = getLivingShadow(context);
            if (livingShadow != null) {
                this.swapTargets(caster, livingShadow);
            }
        }
    }

    private void swapTargets(LivingEntity caster, LivingShadow shadow) {
        Vec3 firstPos = caster.position();
        Vec3 secondPosPos = shadow.position();
        caster.teleportTo(secondPosPos.x, secondPosPos.y, secondPosPos.z);
        shadow.teleportTo(firstPos.x, firstPos.y, firstPos.z);
        this.spawnTeleportParticles(caster, 40);
        this.spawnTeleportParticles(shadow, 40);
        this.removeSkillBuff(caster, SBSkills.STEP_INTO_SHADOW);
        this.removeSkillBuff(caster, SBSkills.LOOK_OVER_HERE);
        this.endSpell();
    }

    private void spawnTeleportParticles(Entity entity, int amount) {
        for (int j = 0; j < amount; j++) {
            this.createSurroundingServerParticles(entity, ParticleTypes.PORTAL, 0.5);
        }
    }

    @Override
    protected int getDuration(SpellContext context) {
        return context.hasSkill(SBSkills.HALL_OF_MIRRORS) ? 80 : super.getDuration(context);
    }

    public Vec3 getTeleportLocation() {
        return this.teleportLocation;
    }

    private void setLivingShadow(int livingShadow) {
        this.spellData.set(LIVING_SHADOW, livingShadow);
    }

    private LivingShadow getLivingShadow(SpellContext context) {
        Entity entity = context.getLevel().getEntity(this.spellData.get(LIVING_SHADOW));
        return entity instanceof LivingShadow livingShadow ? livingShadow : null;
    }

    @Override
    protected void onSpellStop(SpellContext context) {

    }

    @Override
    public boolean shouldRender(SpellContext context) {
        return context.hasSkill(SBSkills.HALL_OF_MIRRORS);
    }

    @Override
    public @UnknownNullability CompoundTag saveData(CompoundTag compoundTag) {
        CompoundTag nbt = super.saveData(compoundTag);
        nbt.putInt("LivingShadowId", this.spellData.get(LIVING_SHADOW));
        return nbt;
    }

    @Override
    public void loadData(CompoundTag nbt) {
        super.loadData(nbt);
        this.spellData.set(LIVING_SHADOW, nbt.getInt("LivingShadowId"));
    }
}
