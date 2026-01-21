package com.ombremoon.spellbound.networking.clientbound;

import com.ombremoon.spellbound.client.gui.toasts.SpellboundToasts;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record SpellLevelUpToastPayload(int level, SpellboundToasts toast, SpellType<?> spellType) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SpellLevelUpToastPayload> TYPE = new CustomPacketPayload.Type<>(CommonClass.customLocation("spell_level_up_toast_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SpellLevelUpToastPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SpellLevelUpToastPayload::level,
            NeoForgeStreamCodecs.enumCodec(SpellboundToasts.class), SpellLevelUpToastPayload::toast,
            ByteBufCodecs.registry(SBSpells.SPELL_TYPE_REGISTRY_KEY), SpellLevelUpToastPayload::spellType,
            SpellLevelUpToastPayload::new
    );


    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
