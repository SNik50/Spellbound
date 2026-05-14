package com.ombremoon.spellbound.common.world.spell.ruin.ice;

import com.ombremoon.spellbound.common.init.SBBlocks;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.common.magic.api.ChanneledSpell;
import com.ombremoon.spellbound.common.magic.api.SpellAnimation;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.ModifierData;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.world.block.entity.IceSheetBlockEntity;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class IceSkateSpell extends ChanneledSpell {
    private static final BlockPredicate PREDICATE =
            BlockPredicate.allOf(
                    BlockPredicate.anyOf(
                            BlockPredicate.matchesTag(new Vec3i(0, 0, 0), BlockTags.AIR),
                            BlockPredicate.replaceable()
                    ),
                    BlockPredicate.not(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.AIR)),
                    BlockPredicate.not(BlockPredicate.matchesBlocks(new Vec3i(0, -1, 0), SBBlocks.ICE_SHEET.get())),
                    BlockPredicate.unobstructed()
            );
    private static final BlockPredicate PREDICATE1 =
            BlockPredicate.allOf(
                    BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                    BlockPredicate.matchesBlocks(Blocks.WATER),
                    BlockPredicate.matchesFluids(Fluids.WATER),
                    BlockPredicate.unobstructed()
            );
    private BlockPos position = BlockPos.ZERO;
    private int offGroundTicks;

    public static Builder<IceSkateSpell> createIceSkateBuilder() {
        return createChannelledSpellBuilder(IceSkateSpell.class)
                .castAnimation((context, spell) -> new SpellAnimation("solar_ray_cast", SpellAnimation.Type.CAST, true))
                .channelAnimation(context -> new SpellAnimation("solar_ray_channel", SpellAnimation.Type.CHANNEL, true))
                .stopChannelAnimation(new SpellAnimation("solar_ray_end", SpellAnimation.Type.CAST, true));
    }
    public IceSkateSpell() {
        super(SBSpells.ICE_SKATE.get(), createIceSkateBuilder());
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
            this.addSkillBuff(
                    caster,
                    SBSkills.ICE_SKATE,
                    CommonClass.customLocation("ice_skate_buff"),
                    BuffCategory.BENEFICIAL,
                    SkillBuff.ATTRIBUTE_MODIFIER,
                    new ModifierData(Attributes.STEP_HEIGHT, new AttributeModifier(CommonClass.customLocation("ice_skate_buff"), 0.6, AttributeModifier.Operation.ADD_VALUE))
            );
            this.position = caster.blockPosition();
        }
    }

    @Override
    protected void onSpellTick(SpellContext context) {
        super.onSpellTick(context);
        LivingEntity caster = context.getCaster();
        Level level = context.getLevel();
        double yaw = Math.toRadians(caster.getYRot());
        Vec3 horizontalDirection = new Vec3(-Math.sin(yaw), -0.75, Math.cos(yaw));
        if (horizontalDirection.lengthSqr() > 1.0) {
            horizontalDirection = horizontalDirection.normalize();
        }

        double speedMod = context.hasSkill(SBSkills.FRICTIONLESS) ? 1.75 : 1.45;
        double strength = 0.6F * speedMod;
        Vec3 motion = horizontalDirection.scale(strength);
        caster.setDeltaMovement(motion);
        caster.hurtMarked = true;

        if (!level.isClientSide) {
            Vec3 origin = caster.position();
            BlockPos blockpos = BlockPos.containing(origin);
            int i = 3;
            if (caster.blockPosition() != this.position) {
                for (BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-i, 0, -i), blockpos.offset(i, 0, i))) {
                    if (blockpos1.distToCenterSqr(origin.x(), (double) blockpos1.getY() + 0.5, origin.z()) < (double) Mth.square(i)
                            && PREDICATE.test((ServerLevel) level, blockpos1)
                            && level.setBlockAndUpdate(blockpos1, SBBlocks.ICE_SHEET.get().defaultBlockState())) {
                        BlockEntity blockEntity = level.getBlockEntity(blockpos1);
                        if (blockEntity instanceof IceSheetBlockEntity iceSheet) {
                            log("Hi");
                        }
                    }
                }

                this.position = caster.blockPosition();
            }

            if (!caster.onGround()) {
                this.offGroundTicks++;
                if (this.offGroundTicks > 15) {
                    this.endSpell();
                }
            } else {
                this.offGroundTicks = 0;
            }
        }
    }

    @Override
    protected void onSpellStop(SpellContext context) {
        super.onSpellStop(context);
        Level level = context.getLevel();
        if (!level.isClientSide) {
            this.removeSkillBuff(context.getCaster(), SBSkills.ICE_SKATE);
        }
    }
}
