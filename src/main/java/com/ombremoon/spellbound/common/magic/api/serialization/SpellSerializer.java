package com.ombremoon.spellbound.common.magic.api.serialization;

import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public interface SpellSerializer<T extends AbstractSpell.Builder<?>> {

    ResourceLocation id();

    MapCodec<T> codec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
}
