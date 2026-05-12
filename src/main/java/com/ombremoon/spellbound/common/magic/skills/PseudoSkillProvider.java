package com.ombremoon.spellbound.common.magic.skills;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBSpells;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.Supplier;

public record PseudoSkillProvider(Optional<ResourceLocation> spellType, ResourceLocation location) implements SkillProvider {
    public static final MapCodec<PseudoSkillProvider> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("spell_type").forGetter(PseudoSkillProvider::spellType),
                    ResourceLocation.CODEC.fieldOf("location").forGetter(PseudoSkillProvider::location)
            ).apply(instance, PseudoSkillProvider::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PseudoSkillProvider> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), PseudoSkillProvider::spellType,
            ResourceLocation.STREAM_CODEC, PseudoSkillProvider::location,
            PseudoSkillProvider::new
    );

    @Override
    public SpellType<?> getSpell() {
        return this.spellType.map(SBSpells.REGISTRY::get).orElse(null);

    }

    @Override
    public ResourceLocation location() {
        return this.location;
    }

    @Override
    public Entry<PseudoSkillProvider> getEntry() {
        return SkillProviderRegistry.PSEUDO;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeResourceLocation(this.location);
    }
}
