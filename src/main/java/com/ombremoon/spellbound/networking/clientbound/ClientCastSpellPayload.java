package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientCastSpellPayload(int entityId, SpellType<?> spellType, int castId, CompoundTag initTag, CompoundTag spellData) implements CustomPacketPayload {
    public static final Type<ClientCastSpellPayload> TYPE = new Type<>(CommonClass.customLocation("client_cast_spell"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientCastSpellPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientCastSpellPayload::entityId,
            ByteBufCodecs.registry(SBSpells.SPELL_TYPE_REGISTRY_KEY), ClientCastSpellPayload::spellType,
            ByteBufCodecs.INT, ClientCastSpellPayload::castId,
            ByteBufCodecs.COMPOUND_TAG, ClientCastSpellPayload::initTag,
            ByteBufCodecs.COMPOUND_TAG, ClientCastSpellPayload::spellData,
            ClientCastSpellPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
