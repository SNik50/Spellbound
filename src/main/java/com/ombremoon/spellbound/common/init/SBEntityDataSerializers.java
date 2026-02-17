package com.ombremoon.spellbound.common.init;

import com.ombremoon.spellbound.common.magic.effects.MagicEffect;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

public class SBEntityDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);

    public static final Supplier<EntityDataSerializer<Optional<MagicEffect>>> MAGIC_EFFECT = ENTITY_DATA_SERIALIZERS.register("magic_effect", () -> EntityDataSerializer.forValueType(ByteBufCodecs.optional(MagicEffect.STREAM_CODEC)));

    public static void register(IEventBus modEventBus) {
        ENTITY_DATA_SERIALIZERS.register(modEventBus);
    }
}
