package com.ombremoon.spellbound.client.particle;

import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class EffectTypes {

    static class BlockEffectType extends EffectType<EffectBuilder.Block> {
        @Override
        public ResourceLocation id() {
            return CommonClass.customLocation("block");
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EffectBuilder.Block> streamCodec() {
            return null;
        }
    }
}
