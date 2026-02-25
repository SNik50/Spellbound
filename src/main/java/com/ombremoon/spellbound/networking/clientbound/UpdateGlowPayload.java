package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.main.CommonClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateGlowPayload(int entityId, boolean remove) implements CustomPacketPayload {
    public static final Type<UpdateGlowPayload> TYPE = new Type<>(CommonClass.customLocation("add_glow_effect"));

    public static final StreamCodec<ByteBuf, UpdateGlowPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateGlowPayload::entityId,
            ByteBufCodecs.BOOL, UpdateGlowPayload::remove,
            UpdateGlowPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
