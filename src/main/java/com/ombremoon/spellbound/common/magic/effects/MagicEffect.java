package com.ombremoon.spellbound.common.magic.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.world.multiblock.Multiblock;
import com.ombremoon.spellbound.common.world.multiblock.type.TransfigurationMultiblock;
import com.ombremoon.spellbound.common.init.SBMagicEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public interface MagicEffect {
    Codec<MagicEffect> CODEC = SBMagicEffects.REGISTRY
            .byNameCodec()
            .dispatch(MagicEffect::getSerializer, Serializer::codec);

    StreamCodec<RegistryFriendlyByteBuf, MagicEffect> STREAM_CODEC = ByteBufCodecs.registry(SBMagicEffects.RITUAL_EFFECT_REGISTRY_KEY)
            .dispatch(MagicEffect::getSerializer, Serializer::streamCodec);


    void onActivated(ServerLevel level, int tier, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern);

    default void onDeactivated(ServerLevel level, int tier, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern) {

    }

    Serializer<? extends MagicEffect> getSerializer();

    default boolean isValid(TransfigurationMultiblock multiblock) {
        return true;
    }

    default boolean isValid(LivingEntity target) {
        return true;
    }

    interface Serializer<T extends MagicEffect> {
        MapCodec<T> codec();

        StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
    }
}
