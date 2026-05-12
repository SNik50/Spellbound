package com.ombremoon.spellbound.common.magic.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.init.SBTags;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

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

    public boolean canImbueStack(ItemStack stack) {
        Imbuement imbuement = stack.get(SBData.IMBUEMENT);
        if (!stack.is(SBTags.Items.IMBUEABLE)) {
            return false;
        }

        return imbuement == null || imbuement.spellType == this.spellType;
    }

    public static boolean isSimilarImbuement(Imbuement imbuement, ItemStack stack) {
        Imbuement other = stack.get(SBData.IMBUEMENT);
        return other != null && imbuement.spellType == other.spellType;
    }
}
