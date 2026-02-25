package com.ombremoon.spellbound.common.world.familiars;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.ombremoon.spellbound.common.init.SBAffinities;
import com.ombremoon.spellbound.common.init.SBTags;
import com.ombremoon.spellbound.common.magic.api.buff.BuffCategory;
import com.ombremoon.spellbound.common.magic.api.buff.SkillBuff;
import com.ombremoon.spellbound.common.magic.api.buff.SpellEventListener;
import com.ombremoon.spellbound.common.magic.api.buff.SpellModifier;
import com.ombremoon.spellbound.common.magic.api.events.DeathEvent;
import com.ombremoon.spellbound.common.magic.familiars.Familiar;
import com.ombremoon.spellbound.common.magic.familiars.FamiliarHandler;
import com.ombremoon.spellbound.common.magic.skills.FamiliarAffinity;
import com.ombremoon.spellbound.common.world.entity.living.familiars.FrogEntity;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class FrogFamiliar extends Familiar<FrogEntity> {
    private static final ResourceLocation SWAMP_BUFF = CommonClass.customLocation("murky_habitat_buff");
    private static final ResourceLocation SUBMERGED_HP = CommonClass.customLocation("submerged_hp");
    private static final ResourceLocation SUBMERGED_ATK = CommonClass.customLocation("submerged_atk");
    private static final HolderSet<Block> FROG_LIGHTS = HolderSet.direct(
            Holder.direct(Blocks.VERDANT_FROGLIGHT),
            Holder.direct(Blocks.OCHRE_FROGLIGHT),
            Holder.direct(Blocks.PEARLESCENT_FROGLIGHT)
    );

    private boolean hasSwampBuff = false;

    public FrogFamiliar(int bond, int rebirths) {
        super(bond, rebirths);
    }

    @Override
    public List<FamiliarAffinity> modifyOwnerAttributes(LivingEntity owner, FamiliarHandler handler, int rebirths, int bond) {
        return List.of(
                addAttributeModifier(owner, SBAffinities.SPECTRAL_HOPS, Attributes.JUMP_STRENGTH, 0.15f, AttributeModifier.Operation.ADD_VALUE),
                addAttributeModifier(owner, SBAffinities.SPECTRAL_HOPS, Attributes.SAFE_FALL_DISTANCE, 1f, AttributeModifier.Operation.ADD_VALUE),
                addAttributeModifier(owner, SBAffinities.ELONGATED_TONGUE, Attributes.BLOCK_INTERACTION_RANGE, 2F, AttributeModifier.Operation.ADD_VALUE),
                addAttributeModifier(owner, SBAffinities.ELONGATED_TONGUE, Attributes.ENTITY_INTERACTION_RANGE, 2F, AttributeModifier.Operation.ADD_VALUE)
        );
    }

    @Override
    public void onSpawn(FamiliarHandler handler, BlockPos spawnPos) {
        super.onSpawn(handler, spawnPos);
        if (hasAffinity(handler, SBAffinities.MAGMA_DIGESTION)) addMagmaDigestionEvent(handler);
        if (hasAffinity(handler, SBAffinities.SLIMEY_EXPULSION)) addSlimeyExpulsionEvent(handler);

        if (hasAffinity(handler,  SBAffinities.MURKY_HABITAT)) {
            boolean inSwamp = handler.getLevel().getBiome(handler.getOwner().blockPosition()).is(Tags.Biomes.IS_SWAMP);
            if (inSwamp) addSwampPotency(handler);
        }

        if (hasAffinity(handler, SBAffinities.SUBMERGED)) {
            if (handler.getLevel().getFluidState(spawnPos).is(FluidTags.WATER)) {
                addAttributeModifier(handler.getActiveEntity(),
                        SBAffinities.SUBMERGED,
                        Attributes.ATTACK_DAMAGE,
                        1F * handler.getRebirths(handler.getSelectedFamiliar()),
                        AttributeModifier.Operation.ADD_VALUE);

                handler.getActiveEntity().addEffect(new MobEffectInstance(MobEffects.REGENERATION, SBAffinities.SUBMERGED.getCooldown(), 0, true, false));
                useAffinity(handler, SBAffinities.SUBMERGED);
            }
        }
    }

    @Override
    public boolean shouldTick(FamiliarHandler handler, int tickCount) {
        return tickCount % 20 == 0;
    }

    @Override
    public void tick(FamiliarHandler handler, int tickCount) {
        super.tick(handler, tickCount);

        if (hasAffinity(handler, SBAffinities.MURKY_HABITAT)) {
            boolean inSwamp = handler.getLevel().getBiome(handler.getOwner().blockPosition()).is(Tags.Biomes.IS_SWAMP);
            if (inSwamp) addSwampPotency(handler);
            else removeSwampPotency(handler);
        }
    }

    @Override
    public void onAffinityOffCooldown(FamiliarHandler handler, FamiliarAffinity affinity) {
        super.onAffinityOffCooldown(handler, affinity);

        if (affinity.equals(SBAffinities.SUBMERGED) && !handler.getLevel().getFluidState(handler.getActiveEntity().blockPosition()).is(FluidTags.WATER)) {
            removeSkillBuff(handler.getActiveEntity(), SBAffinities.SUBMERGED);
        } else if (affinity.equals(SBAffinities.SUBMERGED)) {
            useAffinity(handler, SBAffinities.SUBMERGED);
        }
    }

    @Override
    public void onBondUp(FamiliarHandler handler, int oldLevel, int newLevel) {
        super.onBondUp(handler, oldLevel, newLevel);

        int magmaLevel = SBAffinities.MAGMA_DIGESTION.getRequiredBond();
        if (oldLevel < magmaLevel && newLevel >= magmaLevel) {
            addMagmaDigestionEvent(handler);
        }

        int slimeyLevel = SBAffinities.SLIMEY_EXPULSION.getRequiredBond();
        if (oldLevel < slimeyLevel && newLevel >= slimeyLevel) {
            addSlimeyExpulsionEvent(handler);
        }
    }

    @Override
    public void onRebirth(FamiliarHandler handler, int rebirths) {
        super.onRebirth(handler, rebirths);
        removeEventListener(handler, SBAffinities.MAGMA_DIGESTION.location());
        removeOwnerEventListener(handler, SBAffinities.SLIMEY_EXPULSION.location());
        removeSwampPotency(handler);
    }

    @Override
    public void onRemove(FamiliarHandler handler, BlockPos removePos) {
        super.onRemove(handler, removePos);
        removeEventListener(handler, SBAffinities.MAGMA_DIGESTION.location());
        removeOwnerEventListener(handler, SBAffinities.SLIMEY_EXPULSION.location());
        removeSwampPotency(handler);
    }

    private void addSwampPotency(FamiliarHandler handler) {
        if (hasSwampBuff) return;
        hasSwampBuff = true;
        addSkillBuff(
                handler.getOwner(),
                SBAffinities.MURKY_HABITAT,
                SWAMP_BUFF,
                BuffCategory.BENEFICIAL,
                SkillBuff.SPELL_MODIFIER,
                SpellModifier.MURKY_HABITAT
        );
    }

    private void removeSwampPotency(FamiliarHandler handler) {
        if (!hasSwampBuff) return;
        hasSwampBuff = false;
        removeSkillBuff(handler.getOwner(), SBAffinities.MURKY_HABITAT);
    }

    private void addMagmaDigestionEvent(FamiliarHandler handler) {
        addEventListener(
                handler,
                SpellEventListener.Events.ENTITY_KILL,
                SBAffinities.MAGMA_DIGESTION.location(),
                this::magmaDigestion);
    }

    private void magmaDigestion(DeathEvent event) {
        boolean flag = (SpellUtil.isSummon(event.getCaster()) && SpellUtil.getOwner(event.getCaster()).is(this.getOwner()))
                || event.getCaster().is(getOwner());

        if (flag && getRandom().nextInt(100) < 30) {
            var optional = BuiltInRegistries.ITEM.getTag(SBTags.Items.FROG_LIGHTS);
            ItemStack stack;

            if (optional.isEmpty()) stack = Blocks.PEARLESCENT_FROGLIGHT.asItem().getDefaultInstance();
            else stack = optional.get()
                    .get(getRandom().nextInt(optional.get().size()))
                    .unwrap()
                    .right()
                    .get()
                    .asItem()
                    .getDefaultInstance();

            event.getKilledEntity().spawnAtLocation(stack);
        }
    }

    private void addSlimeyExpulsionEvent(FamiliarHandler handler) {
        addOwnerEventListener(handler,
                SpellEventListener.Events.POST_DAMAGE,
                SBAffinities.SLIMEY_EXPULSION.location(),
                this::slimeyExpulsion);
    }

    private void slimeyExpulsion(com.ombremoon.spellbound.common.magic.api.events.DamageEvent.Post post) {
        if (post.isCancelled()) return;
        if (post.getSource().getEntity().is(this.getOwner())) {
            post.getEntity().addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20)
            );
        }
    }
}
