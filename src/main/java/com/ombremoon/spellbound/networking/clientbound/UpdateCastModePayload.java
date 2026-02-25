package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateCastModePayload(int entityId, boolean castMode) implements CustomPacketPayload {
    public static final Type<UpdateCastModePayload> TYPE = new Type<>(CommonClass.customLocation("update_cast_mode"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateCastModePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateCastModePayload::entityId,
            ByteBufCodecs.BOOL, UpdateCastModePayload::castMode,
            UpdateCastModePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
