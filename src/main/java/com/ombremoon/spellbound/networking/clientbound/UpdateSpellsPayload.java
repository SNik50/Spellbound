package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateSpellsPayload(int entityId, CompoundTag spells) implements CustomPacketPayload {
    public static final Type<UpdateSpellsPayload> TYPE = new Type<>(CommonClass.customLocation("update_spells"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateSpellsPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateSpellsPayload::entityId,
            ByteBufCodecs.COMPOUND_TAG, UpdateSpellsPayload::spells,
            UpdateSpellsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
