package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.client.gui.toasts.PageScrapUnlockedToast;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ScrapToastPayload(ResourceLocation scrap) implements CustomPacketPayload {
    public static final Type<ScrapToastPayload> TYPE = new Type<>(CommonClass.customLocation("scrap_toast_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ScrapToastPayload> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, ScrapToastPayload::scrap,
            ScrapToastPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ScrapToastPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance()
                    .getToasts()
                    .addToast(new PageScrapUnlockedToast(payload.scrap()));
        });
    }
}
