package com.ombremoon.spellbound.networking.clientbound;

import com.mojang.datafixers.util.Either;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.client.photon.converter.EffectData;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TriggerSpellFXPayload(int entityId, SpellType<?> spellType, int castId, Either<EffectData, ResourceLocation> effect) implements CustomPacketPayload {
    public static final Type<TriggerSpellFXPayload> TYPE = new Type<>(CommonClass.customLocation("trigger_spell_fx"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TriggerSpellFXPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, TriggerSpellFXPayload::entityId,
            ByteBufCodecs.registry(SBSpells.SPELL_TYPE_REGISTRY_KEY), TriggerSpellFXPayload::spellType,
            ByteBufCodecs.INT, TriggerSpellFXPayload::castId,
            ByteBufCodecs.either(EffectData.STREAM_CODEC, ResourceLocation.STREAM_CODEC), TriggerSpellFXPayload::effect,
            TriggerSpellFXPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
