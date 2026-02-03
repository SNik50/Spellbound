package com.ombremoon.spellbound.common.magic.api.serialization;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface SpellBuilder {
    StreamCodec<RegistryFriendlyByteBuf, SpellBuilder> STREAM_CODEC = SpellSerializers.STREAM_CODEC.dispatch(SpellBuilder::getSerializer, SpellSerializer::streamCodec);

    SpellSerializer<?> getSerializer();
}
