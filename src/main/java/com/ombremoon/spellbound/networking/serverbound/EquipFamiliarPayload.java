package com.ombremoon.spellbound.networking.serverbound;

import com.ombremoon.spellbound.common.init.SBFamiliars;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EquipFamiliarPayload(ResourceLocation familiar) implements CustomPacketPayload {
    public static final Type<EquipFamiliarPayload> TYPE = new Type<>(CommonClass.customLocation("equip_familiar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EquipFamiliarPayload> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, EquipFamiliarPayload::familiar,
            EquipFamiliarPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(EquipFamiliarPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
           var fam = SBFamiliars.REGISTRY.get(payload.familiar());
           if (fam == null) return;
           SpellUtil.getFamiliarHandler(context.player()).selectFamiliar(fam);
        });
    }
}
