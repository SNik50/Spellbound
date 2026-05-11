package com.ombremoon.spellbound.common.magic.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBSpells;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Imbuement(SpellType<?> spellType, int endTick) {
    public static final Codec<Imbuement> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    SBSpells.REGISTRY.byNameCodec().fieldOf("spell_type").forGetter(Imbuement::spellType),
                    Codec.INT.fieldOf("end_tick").forGetter(Imbuement::endTick
            )
    ).apply(instance, Imbuement::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Imbuement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(SBSpells.SPELL_TYPE_REGISTRY_KEY), Imbuement::spellType,
            ByteBufCodecs.VAR_INT, Imbuement::endTick,
            Imbuement::new
    );
}
