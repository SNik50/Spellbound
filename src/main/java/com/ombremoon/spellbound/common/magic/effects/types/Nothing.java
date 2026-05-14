package com.ombremoon.spellbound.common.magic.effects.types;

import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.init.SBMagicEffects;
import com.ombremoon.spellbound.common.magic.effects.EffectContextParamSets;
import com.ombremoon.spellbound.common.magic.effects.MagicEffect;
import com.ombremoon.spellbound.common.world.multiblock.Multiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record Nothing() implements MagicEffect {
    @Override
    public void onActivated(ServerLevel level, int tier, @Nullable Entity source, LivingEntity target, BlockPos centerPos, Multiblock.MultiblockPattern pattern) {

    }

    @Override
    public MagicEffect.Serializer<? extends MagicEffect> serializer() {
        return SBMagicEffects.NOTHING.get();
    }

    @Override
    public LootContext createContext(ServerLevel level, int tier, @Nullable Entity source, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern, @Nullable DamageSource damageSource) {
        LootParams lootparams = new LootParams.Builder(level)
                .create(LootContextParamSets.EMPTY)/*
                .withParameter(LootContextParams.THIS_ENTITY, caster)
                .create(EffectContextParamSets.EMPTY)*/;
        return new LootContext.Builder(lootparams).create(Optional.empty());
    }

    public static class Serializer implements MagicEffect.Serializer<Nothing> {
        public static final MapCodec<Nothing> CODEC = MapCodec.unit(Nothing::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, Nothing> STREAM_CODEC = StreamCodec.unit(new Nothing());

        @Override
        public MapCodec<Nothing> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, Nothing> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
