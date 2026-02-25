package com.ombremoon.spellbound.common.magic.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBMagicEffects;
import com.ombremoon.spellbound.common.world.multiblock.Multiblock;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public record AddSpellModifier(ResourceKey<DamageType> damageType, float damage, Optional<Integer> sourceId) implements MagicEffect {

    @Override
    public void onActivated(ServerLevel level, int tier, LivingEntity caster, BlockPos centerPos, Multiblock.MultiblockPattern pattern) {
        Entity entity = null;
        if (this.sourceId.isPresent())
            entity = level.getEntity(this.sourceId.get());

        DamageSource source = SpellUtil.damageSource(level, this.damageType, entity, entity);
        caster.hurt(source, this.damage);
    }

    @Override
    public MagicEffect.Serializer<? extends MagicEffect> getSerializer() {
        return SBMagicEffects.DAMAGE_ENTITY.get();
    }

    public static class Serializer implements MagicEffect.Serializer<AddSpellModifier> {
        public static final MapCodec<AddSpellModifier> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        ResourceKey.codec(Registries.DAMAGE_TYPE).fieldOf("damage_type").forGetter(AddSpellModifier::damageType),
                        Codec.FLOAT.fieldOf("damage").forGetter(AddSpellModifier::damage),
                        Codec.INT.optionalFieldOf("source_id").forGetter(AddSpellModifier::sourceId)
                ).apply(instance, AddSpellModifier::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, AddSpellModifier> STREAM_CODEC = StreamCodec.composite(
                ResourceKey.streamCodec(Registries.DAMAGE_TYPE), AddSpellModifier::damageType,
                ByteBufCodecs.FLOAT, AddSpellModifier::damage,
                ByteBufCodecs.optional(ByteBufCodecs.VAR_INT), AddSpellModifier::sourceId,
                AddSpellModifier::new
        );

        @Override
        public MapCodec<AddSpellModifier> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AddSpellModifier> streamCodec() {
            return STREAM_CODEC;
        }
    }
}


