package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.magic.familiars.FamiliarHandler;
import com.ombremoon.spellbound.common.world.entity.living.familiars.SBFamiliarEntity;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncFamiliarPayload(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<SyncFamiliarPayload> TYPE = new Type<>(CommonClass.customLocation("sync_familiar_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncFamiliarPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SyncFamiliarPayload::tag,
            SyncFamiliarPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncFamiliarPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var handler = context.player().getData(SBData.FAMILIAR_HANDLER.get());
            handler.deserializeNBT(context.player().level().registryAccess(), payload.tag);
        });
    }
}
