package com.ombremoon.spellbound.common.magic.api.serialization;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.ombremoon.spellbound.common.magic.api.AbstractSpell;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SerializationUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class SpellSerializers {
    private static final Map<ResourceLocation, SpellSerializer<?>> REGISTRY = new HashMap<>();
    public static final Codec<SpellSerializer<?>> CODEC = ResourceLocation.CODEC
            .comapFlatMap(
                    location -> {
                        SpellSerializer<?> serializer = REGISTRY.get(location);
                        return serializer != null
                                ? DataResult.success(serializer)
                                : DataResult.error(() -> "No SpellSerializer with key: " + location);
                    },
                    SpellSerializer::id
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellSerializer<?>> STREAM_CODEC = SerializationUtil.REGISTRY_RESOURCE_STREAM_CODEC
            .map(SpellSerializers::getProjectileFromLocation, SpellSerializer::id);

    public static SpellSerializer<?> getProjectileFromLocation(ResourceLocation resourceLocation) {
        return REGISTRY.getOrDefault(resourceLocation, null);
    }

    public static final SpellSerializer<AbstractSpell.Builder<?>> ABSTRACT = register("abstract", new AbstractSpell.Serializer());

    private static <T extends AbstractSpell.Builder<?>> SpellSerializer<T> register(String name, SpellSerializer<T> serializer) {
        REGISTRY.put(CommonClass.customLocation(name), serializer);
        return serializer;
    }
}
