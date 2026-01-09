package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record PathLevelUpToastPayload(int level, int toast) implements CustomPacketPayload {
    public static final Type<PathLevelUpToastPayload> TYPE = new Type<>(CommonClass.customLocation("path_level_up_toast_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PathLevelUpToastPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            PathLevelUpToastPayload::level,
            ByteBufCodecs.INT,
            PathLevelUpToastPayload::toast,
            PathLevelUpToastPayload::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
