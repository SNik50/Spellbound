package com.ombremoon.spellbound.common.magic.skills;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.init.SBAffinities;
import com.ombremoon.spellbound.common.init.SBSkills;
import com.ombremoon.spellbound.common.magic.api.SpellType;
import com.ombremoon.spellbound.common.magic.effects.TickProvider;
import com.ombremoon.spellbound.common.magic.effects.TickProviderRegistry;
import com.ombremoon.spellbound.common.magic.effects.TickProviderSerializer;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public interface SkillProvider {
    Logger LOGGER = Constants.LOG;
    Codec<SkillProvider> CODEC = SkillProviderRegistry.CODEC.dispatchStable(SkillProvider::getEntry, SkillProvider.Entry::codec);
    StreamCodec<RegistryFriendlyByteBuf, SkillProvider> STREAM_CODEC = SkillProviderRegistry.STREAM_CODEC.dispatch(SkillProvider::getEntry, SkillProvider.Entry::streamCodec);

    ResourceLocation location();

    Entry<?> getEntry();

    void encode(RegistryFriendlyByteBuf buf);

    default Component getName() {
        return Component.empty();
    }

    default ResourceLocation getTexture() {
        return CommonClass.customLocation("");
    }

    default SpellType<?> getSpell() {
        return null;
    }

    default String string() {
        return "Skill: [location: " + this.location().toString() + ", type: " + getEntry().id().toString() + "]";
    }

    interface Entry<T extends SkillProvider> {

        ResourceLocation id();

        MapCodec<T> codec();

        StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
    }
}
