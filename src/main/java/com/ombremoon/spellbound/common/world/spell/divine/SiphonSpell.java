package com.ombremoon.spellbound.common.world.spell.divine;

import com.ombremoon.spellbound.common.init.*;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import com.ombremoon.spellbound.common.magic.api.ChanneledSpell;
import com.ombremoon.spellbound.common.magic.api.RadialSpell;
import com.ombremoon.spellbound.common.magic.api.SpellAnimation;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.ModifierData;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.magic.api.buff.SpellEventListener;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashSet;

public class SiphonSpell extends ChanneledSpell implements RadialSpell {
    private static final ResourceLocation SIPHON = CommonClass.customLocation("siphon");
    private static final ResourceLocation GRIM_REACH = CommonClass.customLocation("grim_reach");
    private static final ResourceLocation WITHERING = CommonClass.customLocation("withering");
    private static final ResourceLocation OVERHEAL = CommonClass.customLocation("overheal");
    private static final ResourceLocation ARCANE_FEEDBACK = CommonClass.customLocation("arcane_feedback");
    private static final ResourceLocation IRON_MAIDEN = CommonClass.customLocation("iron_maiden");
    private LivingEntity target;
    private int endTick;
    private int overhealStacks;
    private AbstractSpell targetSpell;
    private long targetCastTick;

    private static Builder<SiphonSpell> createSiphonBuilder() {
        return createChannelledSpellBuilder(SiphonSpell.class)
                .castCondition((context, spell) -> (context.isChoice(SBSkills.HARVEST) && context.hasCatalyst(SBItems.HOLY_SHARD.get())) || context.getTarget() instanceof LivingEntity)
                .castAnimation((context, spell) -> new SpellAnimation("solar_ray_cast", SpellAnimation.Type.CAST, true))
                .channelAnimation(context -> new SpellAnimation("solar_ray_channel", SpellAnimation.Type.CHANNEL, true))
                .stopChannelAnimation(new SpellAnimation("solar_ray_end", SpellAnimation.Type.CAST, true))
                .negativeScaling();
    }

    public SiphonSpell() {
        super(SBSpells.SIPHON.get(), createSiphonBuilder());
    }

    @Override
    public void registerSkillTooltips() {

    }

    @Override
    protected void onSpellStart(SpellContext context) {
        super.onSpellStart(context);
        Level level = context.getLevel();
        LivingEntity caster = context.getCaster();
        if (!level.isClientSide) {
            this.target = context.getTarget() instanceof LivingEntity livingEntity && !context.isChoice(SBSkills.HARVEST)? livingEntity : null;
            if (context.hasSkill(SBSkills.PARASITIC_LINK) && this.target != null) {
                var targetHandler = SpellUtil.getSpellHandler(this.target);
                this.targetSpell = targetHandler.previouslyCastSpell;
                this.targetCastTick = targetHandler.lastCastTick;
            }

            this.addEventBuff(
                    caster,
                    SBSkills.SIPHON,
                    BuffCategory.BENEFICIAL,
                    SpellEventListener.Events.DEALT_DAMAGE_POST,
                    SIPHON,
                    post -> {
                        if (context.hasSkill(SBSkills.GLUTTONY) && caster.getHealth() >= caster.getMaxHealth() && caster instanceof Player player) {
                            if (!player.getFoodData().needsFood()) {
                                if (context.hasSkill(SBSkills.OVERHEAL) && this.overhealStacks <= 5) {
                                    if (this.overhealStacks < 5) {
                                        this.overhealStacks++;
                                        this.removeSkillBuff(caster, SBSkills.OVERHEAL);
                                    }

                                    this.addSkillBuff(
                                            caster,
                                            SBSkills.OVERHEAL,
                                            OVERHEAL,
                                            BuffCategory.BENEFICIAL,
                                            SkillBuff.ATTRIBUTE_MODIFIER,
                                            new ModifierData(Attributes.MAX_HEALTH, new AttributeModifier(OVERHEAL, 1.0 * this.overhealStacks, AttributeModifier.Operation.ADD_VALUE)),
                                            100
                                    );
                                }
                            } else {
                                player.getFoodData().eat(1, 1.0F);
                            }
                        } else {
                            this.heal(caster, potency(post.getNewDamage()));
                        }
                    }
            );

            if (context.hasSkill(SBSkills.GRIM_REACH)) {
                this.addSkillBuff(
                        caster,
                        SBSkills.GRIM_REACH,
                        GRIM_REACH,
                        BuffCategory.BENEFICIAL,
                        SkillBuff.ATTRIBUTE_MODIFIER,
                        new ModifierData(SBAttributes.CAST_RANGE, new AttributeModifier(GRIM_REACH, 2.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                );
            }

            if (context.hasSkill(SBSkills.IRON_MAIDEN)) {
                this.addEventBuff(
                        caster,
                        SBSkills.IRON_MAIDEN,
                        BuffCategory.BENEFICIAL,
                        SpellEventListener.Events.PRE_DAMAGE,
                        IRON_MAIDEN,
                        pre -> {
                            DamageSource source = pre.getSource();
                            if (this.target != null && source.getEntity() == this.target) {
                                pre.setNewDamage(pre.getOriginalDamage() * 0.75F);
                            }
                        }
                );
            }
        }
    }

    @Override
    protected void onSpellTick(SpellContext context) {
        super.onSpellTick(context);
        LivingEntity caster = context.getCaster();
        Level level = context.getLevel();
        if (!level.isClientSide) {
            boolean flag = true;

            if (this.tickCount % 20 == 0) {
                var targets = new HashSet<LivingEntity>();
                if (context.isChoice(SBSkills.HARVEST) && context.hasCatalyst(SBItems.HOLY_SHARD.get())) {
                    targets.addAll(level.getEntitiesOfClass(LivingEntity.class, this.getInflatedBB(caster, potency(5))));
                    flag = false;
                } else {
                    Entity target = context.getTarget();
                    if (target instanceof LivingEntity livingEntity && (this.target == null || !this.target.is(livingEntity))) {
                        this.target = livingEntity;
                        targets.add(livingEntity);
                    }

                    if (target != null) {
                        this.endTick = this.tickCount + 60;
                    }

                    if (!(target instanceof LivingEntity) && !context.hasSkill(SBSkills.UNRELENTING)) {
                        this.endSpell();
                        return;
                    } else if (target == null && context.hasSkill(SBSkills.UNRELENTING) && this.target instanceof LivingEntity) {
                        if (this.tickCount >= this.endTick) {
                            this.endSpell();
                            return;
                        }
                    } else if (this.target == null) {
                        this.endSpell();
                        return;
                    }

                    targets.add(this.target);
                }

                if (this.target != null && context.hasSkill(SBSkills.PARASITIC_LINK)) {
                    var targetHandler = SpellUtil.getSpellHandler(this.target);
                    this.targetSpell = targetHandler.previouslyCastSpell;
                    this.targetCastTick = targetHandler.lastCastTick;
                }

                for (LivingEntity entity : targets) {
                    if (context.isChoice(SBSkills.SOUL_TAP)) {
                        if (!this.consumeMana(entity, potency(1)) && context.hasSkill(SBSkills.ARCANE_FEEDBACK)) {
                            this.hurt(entity, 1);
                            this.addSkillBuff(
                                    entity,
                                    SBSkills.ARCANE_FEEDBACK,
                                    ARCANE_FEEDBACK,
                                    BuffCategory.HARMFUL,
                                    SkillBuff.MOB_EFFECT,
                                    new MobEffectInstance(SBEffects.SILENCED, 60),
                                    60
                            );
                        }
                    } else if (this.hurt(entity, 1)) {
                        if (context.hasSkill(SBSkills.WITHERING)) {
                            this.addSkillBuff(
                                    entity,
                                    SBSkills.WITHERING,
                                    WITHERING,
                                    BuffCategory.HARMFUL,
                                    SkillBuff.MOB_EFFECT,
                                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, false),
                                    100
                            );
                        }
                    }
                }
            }

            if (context.isChoice(SBSkills.SOUL_TAP) && context.hasSkill(SBSkills.PARASITIC_LINK) && flag && this.targetSpell != null && this.targetCastTick == level.getGameTime()) {
                this.consumeMana(caster, potency(-this.targetSpell.getManaCost(this.target)));
            }
        }
    }

    @Override
    protected void onSpellStop(SpellContext context) {
        super.onSpellStop(context);
        LivingEntity caster = context.getCaster();
        if (!context.getLevel().isClientSide) {
            this.removeSkillBuff(caster, SBSkills.SIPHON);
            this.removeSkillBuff(caster, SBSkills.GRIM_REACH);
            this.removeSkillBuff(caster, SBSkills.IRON_MAIDEN);
        }
    }
}
