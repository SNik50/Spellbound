package com.ombremoon.spellbound.common.magic.skills;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.init.SBAffinities;
import com.ombremoon.spellbound.main.CommonClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class FamiliarAffinity implements SkillProvider {
    public static final MapCodec<FamiliarAffinity> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("identifier").forGetter(FamiliarAffinity::location),
                    Codec.INT.fieldOf("cooldown").forGetter(FamiliarAffinity::getCooldown),
                    Codec.INT.fieldOf("required_bond").forGetter(FamiliarAffinity::getRequiredBond)
            ).apply(instance, FamiliarAffinity::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, FamiliarAffinity> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, FamiliarAffinity::location,
            ByteBufCodecs.VAR_INT, FamiliarAffinity::getCooldown,
            ByteBufCodecs.VAR_INT, FamiliarAffinity::getRequiredBond,
            FamiliarAffinity::new
    );
    private final ResourceLocation identifier;
    private final int cooldown;
    private final int requiredBond;

    public FamiliarAffinity(ResourceLocation identifier, int requiredBond) {
        this(identifier, 0, requiredBond);
    }

    public FamiliarAffinity(ResourceLocation identifier, int cooldown, int requiredBond) {
        this.identifier = identifier;
        this.cooldown = cooldown;
        this.requiredBond = requiredBond;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getRequiredBond() {
        return requiredBond;
    }

    @Override
    public Component getName() {
        return Component.translatable(identifier.getNamespace() + ".affinity." + identifier.getPath());
    }

    public Component getDescription() {
        return Component.translatable(identifier.getNamespace() + ".affinity.description." + identifier.getPath());
    }

    @Override
    public ResourceLocation location() {
        return identifier;
    }

    @Override
    public Entry<FamiliarAffinity> getEntry() {
        return SkillProviderRegistry.AFFINITY;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buf) {
        ResourceLocation.STREAM_CODEC.encode(buf, location());
    }
}
