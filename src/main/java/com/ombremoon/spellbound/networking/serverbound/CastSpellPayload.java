package com.ombremoon.spellbound.networking.serverbound;

import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CastSpellPayload(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<CastSpellPayload> TYPE = new Type<>(CommonClass.customLocation("cast_spell"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CastSpellPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, CastSpellPayload::tag,
            CastSpellPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
