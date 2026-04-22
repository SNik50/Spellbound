package com.ombremoon.spellbound.client.particle;

import com.lowdragmc.photon.client.fx.FXEffectExecutor;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SerializationUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class EffectType<T extends EffectBuilder<? extends FXEffectExecutor>> {
    private static final Map<ResourceLocation, EffectType<?>> REGISTRY = new HashMap<>();

    public abstract ResourceLocation id();

    public abstract StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

    public static final EffectType<EffectBuilder.Block> BLOCK = register("block", new EffectTypes.BlockEffectType());

    public static final StreamCodec<RegistryFriendlyByteBuf, EffectType<?>> STREAM_CODEC = SerializationUtil.REGISTRY_RESOURCE_STREAM_CODEC
            .map(EffectType::getEffectTypeFromLocation, EffectType::id);

    public static EffectType<?> getEffectTypeFromLocation(ResourceLocation resourceLocation) {
        return REGISTRY.getOrDefault(resourceLocation, null);
    }

    private static <S extends EffectType<?>> S register(String name, S provider) {
        REGISTRY.put(CommonClass.customLocation(name), provider);
        return provider;
    }
}
