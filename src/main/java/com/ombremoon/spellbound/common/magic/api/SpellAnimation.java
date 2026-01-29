package com.ombremoon.spellbound.common.magic.api;

import com.ombremoon.spellbound.client.AnimationHelper;
import com.ombremoon.spellbound.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record SpellAnimation(ResourceLocation animation, Type type, boolean stationary) {
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellAnimation> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, SpellAnimation::animation,
            NeoForgeStreamCodecs.enumCodec(Type.class), SpellAnimation::type,
            ByteBufCodecs.BOOL, SpellAnimation::stationary,
            SpellAnimation::new
    );

    //AnimProperties
    //isMotionTracking

    public SpellAnimation(String animation, Type type, boolean stationary) {
        this(CommonClass.customLocation(animation), type, stationary);
    }

    public enum Type {
        CAST(true, AnimationHelper.SPELL_CAST_ANIMATION),
        CHANNEL(true, AnimationHelper.SPELL_CAST_ANIMATION),
        MOVEMENT(false, AnimationHelper.MOVEMENT_ANIMATION);

        private final boolean stationary;
        private final ResourceLocation animationLayer;

        Type(boolean stationary, ResourceLocation animationLayer) {
            this.stationary = stationary;
            this.animationLayer = animationLayer;
        }

        public boolean isStationary() {
            return stationary;
        }

        public ResourceLocation getAnimationLayer() {
            return this.animationLayer;
        }
    }
}
