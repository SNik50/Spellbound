package com.ombremoon.spellbound.client.photon.converter;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public abstract class EffectType<T extends EffectData> {

    public abstract ResourceLocation id();

    public abstract StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

    @Override
    public String toString() {
        return this.id().toString();
    }
}
