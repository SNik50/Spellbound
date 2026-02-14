package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.main.CommonClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record UpdateInvisibilityPayload(int entityId, Optional<MobEffectInstance> effect) implements CustomPacketPayload {
    public static final Type<UpdateInvisibilityPayload> TYPE = new Type<>(CommonClass.customLocation("update_invisibility"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateInvisibilityPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateInvisibilityPayload::entityId,
            ByteBufCodecs.optional(MobEffectInstance.STREAM_CODEC), UpdateInvisibilityPayload::effect,
            UpdateInvisibilityPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
