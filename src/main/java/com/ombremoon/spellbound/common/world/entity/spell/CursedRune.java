package com.ombremoon.spellbound.common.world.entity.spell;

import com.lowdragmc.photon.client.fx.FXEffectExecutor;
import com.ombremoon.spellbound.client.particle.EffectBuilder;
import com.ombremoon.spellbound.common.init.SBEntityDataSerializers;
import com.ombremoon.spellbound.common.magic.effects.MagicEffect;
import com.ombremoon.spellbound.common.world.entity.VFXSpellEntity;
import com.ombremoon.spellbound.common.world.spell.deception.CursedRuneSpell;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Predicate;

public class CursedRune extends VFXSpellEntity<CursedRuneSpell> {
    private static final EntityDataAccessor<Optional<MagicEffect>> RUNE_EFFECT = SynchedEntityData.defineId(CursedRune.class, SBEntityDataSerializers.MAGIC_EFFECT.get());

    public CursedRune(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected EffectBuilder<? extends FXEffectExecutor> getEffect() {
        return null;
    }

    @Override
    protected ResourceLocation getEffectLocation() {
        return null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RUNE_EFFECT, Optional.empty());
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            Optional<MagicEffect> optional = this.getRuneEffect();
            if (optional.isPresent()) {
                MagicEffect effect = optional.get();
                var list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), this.canActivateRune(effect));
                if (!list.isEmpty()) {
                    for (LivingEntity entity : list) {
                        if (!this.isOwner(entity) && effect.isValid(entity)) {
                            effect.onActivated((ServerLevel) this.level(), 1, entity, this.blockPosition(), null);
                        }
                    }

                    //Play Rune Effect
                }
            }
        }
    }

    private Predicate<LivingEntity> canActivateRune(MagicEffect effect) {
        return livingEntity -> {
            Entity summoner = this.getSummoner();
            if (summoner instanceof LivingEntity living && !SpellUtil.CAN_ATTACK_ENTITY.test(living, livingEntity)) {
                return false;
            }

            return effect.isValid(livingEntity);
        };
    }

    public Optional<MagicEffect> getRuneEffect() {
        return this.entityData.get(RUNE_EFFECT);
    }

    public void setRuneEffect(MagicEffect effect) {
        this.entityData.set(RUNE_EFFECT, Optional.of(effect));
    }
}
