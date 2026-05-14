package com.ombremoon.spellbound.common.world.familiars;

import com.ombremoon.sentinellib.api.box.OBBSentinelBox;
import com.ombremoon.sentinellib.api.box.SentinelBox;
import com.ombremoon.sentinellib.common.ISentinel;
import com.ombremoon.spellbound.common.init.SBAffinities;
import com.ombremoon.spellbound.common.init.SBAttributes;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.ModifierData;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.magic.familiars.Familiar;
import com.ombremoon.spellbound.common.magic.familiars.FamiliarHandler;
import com.ombremoon.spellbound.common.magic.skills.FamiliarAffinity;
import com.ombremoon.spellbound.common.world.entity.living.familiars.OwlEntity;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.Mod;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyHostileSensor;

import java.util.List;

public class OwlFamiliar extends Familiar<OwlEntity> {
    public static final ResourceLocation SPEED_BUFF = CommonClass.customLocation("cloudless_speed_buff");
    private boolean hasSpeed = false;
    private static final OBBSentinelBox TWISTED_HEAD_OBB = createTwistedHead();

    private static OBBSentinelBox createTwistedHead() {
        return OBBSentinelBox.Builder.of("twisted_head")
                .sizeAndOffset(10, 0, 5F, -10F)
                .moverType(SentinelBox.MoverType.HEAD_NO_X)
                .noDuration(entity -> false)
                .activeTicks(((entity, integer) -> integer % 10 == 0))
                .attackCondition(((entity, livingEntity) -> false))
                .onCollisionTick(((entity, livingEntity) -> {
                    if (entity instanceof LivingEntity caster && !SpellUtil.isSummonOf(livingEntity, caster) && (livingEntity instanceof Enemy || livingEntity instanceof Player)) {
                        caster.level().playSound(null, livingEntity.blockPosition(), SoundEvents.ALLAY_DEATH, SoundSource.HOSTILE, 1F, 1F);
                        var handler = SpellUtil.getFamiliarHandler(caster);
                        handler.getActiveFamiliar().useAffinity(handler, SBAffinities.TWISTED_HEAD);
                        ISentinel boxOwner = (ISentinel) caster;
                        boxOwner.removeSentinelInstance(TWISTED_HEAD_OBB);

                        if (handler.getActiveEntity() instanceof OwlEntity owl) {
                            if (owl.isIdle()) {
                                owl.triggerAnim(OwlEntity.TWISTED_HEAD, "twist");
                            }
                        }
                    }
                }))
                .build();
    }

    public OwlFamiliar(int bond, int rebirths) {
        super(bond, rebirths);
    }

    @Override
    public List<FamiliarAffinity> modifyFamiliarAttributes(LivingEntity familiar, FamiliarHandler handler, int rebirths, int bond) {
        return List.of(
                addAttributeModifier(familiar, SBAffinities.STEEL_FEATHERS, Attributes.MAX_HEALTH, 1.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
    }

    @Override
    public List<FamiliarAffinity> modifyOwnerAttributes(LivingEntity owner, FamiliarHandler handler, int rebirths, int bond) {
        return List.of(
                addAttributeModifier(owner, SBAffinities.OWL_VISION, SBAttributes.CAST_RANGE, 1.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
    }

    @Override
    public void onSpawn(FamiliarHandler handler, BlockPos spawnPos) {
        super.onSpawn(handler, spawnPos);
        if (hasAffinity(handler, SBAffinities.NIGHTS_EYE))
            handler.getOwner().addEffect(new MobEffectInstance(
                    MobEffects.NIGHT_VISION,
                    -1,
                    1, true, true
                )
            );

        if (hasAffinity(handler, SBAffinities.CLOUDLESS_SPEED)) {
            boolean flag = handler.getOwner().position().y() >= 120;
            if (!hasSpeed && flag) {
                hasSpeed = true;
                addSkillBuff(
                        handler.getOwner(),
                        SBAffinities.CLOUDLESS_SPEED,
                        SPEED_BUFF,
                        BuffCategory.BENEFICIAL,
                        SkillBuff.ATTRIBUTE_MODIFIER,
                        new ModifierData(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_BUFF, 1.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                );
            }
        }

        if (hasAffinity(handler, SBAffinities.TWISTED_HEAD)) {
            ISentinel boxOwner = (ISentinel) handler.getOwner();
            boxOwner.triggerSentinelBox(TWISTED_HEAD_OBB);
        }
    }

    @Override
    public boolean shouldTick(FamiliarHandler handler, int tickCount) {
        return tickCount % 20 == 0;
    }

    @Override
    public void tick(FamiliarHandler handler, int tickCount) {
        super.tick(handler, tickCount);

        if (hasAffinity(handler, SBAffinities.CLOUDLESS_SPEED)) {
            boolean flag = handler.getOwner().position().y() >= 120;
            if (!hasSpeed && flag) {
                hasSpeed = true;
                addSkillBuff(
                        handler.getOwner(),
                        SBAffinities.CLOUDLESS_SPEED,
                        SPEED_BUFF,
                        BuffCategory.BENEFICIAL,
                        SkillBuff.ATTRIBUTE_MODIFIER,
                        new ModifierData(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_BUFF, 1.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
                );
            } else if (hasSpeed && !flag) {
                hasSpeed = false;
                removeSkillBuff(handler.getOwner(), SBAffinities.CLOUDLESS_SPEED);
            }
        }
    }

    @Override
    public void onBondUp(FamiliarHandler handler, int oldLevel, int newLevel) {
        super.onBondUp(handler, oldLevel, newLevel);

        if (hasAffinity(handler, SBAffinities.TWISTED_HEAD)) {
            ISentinel boxOwner = (ISentinel) handler.getOwner();
            boxOwner.triggerSentinelBox(TWISTED_HEAD_OBB);
        }
    }

    @Override
    public void onRebirth(FamiliarHandler handler, int rebirths) {
        super.onRebirth(handler, rebirths);

        ISentinel boxOwner = (ISentinel) handler.getOwner();
        boxOwner.removeSentinelInstance(TWISTED_HEAD_OBB);

        removeSkillBuff(handler.getOwner(), SBAffinities.CLOUDLESS_SPEED);
    }

    @Override
    public void onRemove(FamiliarHandler handler, BlockPos removePos) {
        super.onRemove(handler, removePos);

        if (hasAffinity(handler, SBAffinities.TWISTED_HEAD)) {
            ISentinel boxOwner = (ISentinel) handler.getOwner();
            boxOwner.removeSentinelInstance(TWISTED_HEAD_OBB);
        }

        handler.getOwner().removeEffect(MobEffects.NIGHT_VISION);

        removeSkillBuff(handler.getOwner(), SBAffinities.CLOUDLESS_SPEED);
    }

    @Override
    public void onAffinityOffCooldown(FamiliarHandler handler, FamiliarAffinity affinity) {
        super.onAffinityOffCooldown(handler, affinity);

        if (affinity.equals(SBAffinities.TWISTED_HEAD)) {
            ISentinel boxOwner = (ISentinel) handler.getOwner();
            boxOwner.triggerSentinelBox(TWISTED_HEAD_OBB);
        }
    }
}
