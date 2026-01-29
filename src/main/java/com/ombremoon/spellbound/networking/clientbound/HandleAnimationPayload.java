package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.common.magic.api.SpellAnimation;
import com.ombremoon.spellbound.main.CommonClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public record HandleAnimationPayload(String playerId, SpellAnimation animation, float castSpeed, boolean stopAnimation) implements CustomPacketPayload {
    public static final Type<HandleAnimationPayload> TYPE =
            new Type<>(CommonClass.customLocation("handle_animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HandleAnimationPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, HandleAnimationPayload::playerId,
            SpellAnimation.STREAM_CODEC, HandleAnimationPayload::animation,
            ByteBufCodecs.FLOAT, HandleAnimationPayload::castSpeed,
            ByteBufCodecs.BOOL, HandleAnimationPayload::stopAnimation,
            HandleAnimationPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
