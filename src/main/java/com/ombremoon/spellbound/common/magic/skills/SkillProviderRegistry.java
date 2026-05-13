package com.ombremoon.spellbound.common.magic.skills;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.util.SerializationUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class SkillProviderRegistry {
    private static final Map<ResourceLocation, SkillProvider.Entry<?>> REGISTRY = new HashMap<>();
    public static final Codec<SkillProvider.Entry<?>> CODEC = ResourceLocation.CODEC
            .comapFlatMap(
                    location -> {
                        SkillProvider.Entry<?> serializer = REGISTRY.get(location);
                        return serializer != null
                                ? DataResult.success(serializer)
                                : DataResult.error(() -> "No Skill Provider Entry with key: " + location);
                    },
                    SkillProvider.Entry::id
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, SkillProvider.Entry<?>> STREAM_CODEC = SerializationUtil.REGISTRY_RESOURCE_STREAM_CODEC
            .map(SkillProviderRegistry::getSkillProviderEntryFromLocation, SkillProvider.Entry::id);

    public static final SkillProvider.Entry<Skill> SKILL = createProvider("skill", MapCodec.assumeMapUnsafe(SBSkills.REGISTRY.byNameCodec()), ByteBufCodecs.registry(SBSkills.SKILL_REGISTRY_KEY));
    public static final SkillProvider.Entry<FamiliarAffinity> AFFINITY = createProvider("affinity", FamiliarAffinity.MAP_CODEC, FamiliarAffinity.STREAM_CODEC);
    public static final SkillProvider.Entry<PseudoSkillProvider> PSEUDO = createProvider("psuedo", PseudoSkillProvider.MAP_CODEC, PseudoSkillProvider.STREAM_CODEC);


    public static void initSkillProviders() {
        register(SKILL);
        register(AFFINITY);
        register(PSEUDO);
        //TODO: ADD EVENT
    }

    public static SkillProvider.Entry<?> getSkillProviderEntryFromLocation(ResourceLocation resourceLocation) {
        return REGISTRY.getOrDefault(resourceLocation, null);
    }

    private static <S extends SkillProvider> SkillProvider.Entry<S> createProvider(String name, MapCodec<S> codec, StreamCodec<RegistryFriendlyByteBuf, S> streamCodec) {
        ResourceLocation id = CommonClass.customLocation(name);
        SkillProvider.Entry<S> provider = new SkillProvider.Entry<>() {
            @Override
            public ResourceLocation id() {
                return id;
            }

            @Override
            public MapCodec<S> codec() {
                return codec;
            }

            @Override
            public StreamCodec<RegistryFriendlyByteBuf, S> streamCodec() {
                return streamCodec;
            }
        };
        REGISTRY.put(CommonClass.customLocation(name), provider);
        return provider;
    }

    private static <S extends SkillProvider> void register(SkillProvider.Entry<S> entry) {
        ResourceLocation id = entry.id();
        REGISTRY.put(id, entry);
    }
}
