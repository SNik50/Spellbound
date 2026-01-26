package com.ombremoon.spellbound.common.magic.api;

import com.ombremoon.spellbound.common.events.EventFactory;
import com.ombremoon.spellbound.common.magic.SpellContext;
import com.ombremoon.spellbound.main.CommonClass;
import com.ombremoon.spellbound.networking.PayloadHandler;
import com.ombremoon.spellbound.util.SpellUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The main class most spells will extend from. Primary utility is to handle spells casting animations.
 */
public abstract class AnimatedSpell extends AbstractSpell {
    private final Function<SpellContext, SpellAnimation> castAnimation;

    public static <T extends AnimatedSpell> Builder<T> createSimpleSpellBuilder(Class<T> spellClass) {
        return new Builder<>();
    }

    public AnimatedSpell(SpellType<?> spellType, Builder<?> builder) {
        super(spellType, EventFactory.getAnimatedBuilder(spellType, builder));
        this.castAnimation = builder.castAnimation;
    }

    @Override
    public void onCastStart(SpellContext context) {
        super.onCastStart(context);
        LivingEntity caster = context.getCaster();
        SpellAnimation animation = this.castAnimation.apply(context);
        if (animation != null && caster instanceof Player player) {
            this.playAnimation(player, animation);
        }
    }

    /**
     * Plays an animation for the player. This is called server-side for all players to see the animation
     * @param player The player performing the animation
     * @param animation The animation information
     */
    protected void playAnimation(Player player, SpellAnimation animation) {
        var handler = SpellUtil.getSpellHandler(player);
        handler.playAnimation(player, animation, SpellUtil.getCastSpeed(player));
    }

    protected void stopAnimation(Player player, SpellAnimation animation) {
        var handler = SpellUtil.getSpellHandler(player);
        handler.stopAnimation(player, animation);
    }

    protected void playMovementAnimation(Player player, ResourceLocation movementAnimation, @Nullable SpellAnimation fallbackAnimation) {
        var handler = SpellUtil.getSpellHandler(player);
        SpellAnimation animation = new SpellAnimation(movementAnimation, SpellAnimation.Type.CAST, false);
        if (handler.isMoving() && !handler.movementDirty) {
            handler.movementDirty = true;
            playAnimation(player, animation);
        } else if (!handler.isMoving() && handler.movementDirty) {
            handler.movementDirty = false;
            if (fallbackAnimation != null) {
                playAnimation(player, fallbackAnimation);
            } else {
                stopAnimation(player, animation);
            }
        }
    }

    public static class Builder<T extends AnimatedSpell> extends AbstractSpell.Builder<T> {
        protected Function<SpellContext, SpellAnimation> castAnimation = context -> new SpellAnimation(CommonClass.customLocation("simple_cast"), SpellAnimation.Type.CAST, true);

        public Builder<T> manaCost(int manaCost) {
            this.manaCost = manaCost;
            return this;
        }

        public Builder<T> duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder<T> baseDamage(float baseDamage) {
            this.baseDamage = baseDamage;
            return this;
        }

        public Builder<T> xpModifier(float modifier) {
            this.xpModifier = modifier;
            return this;
        }
        public Builder<T> castTime(int castTime) {
            this.castTime = castTime;
            return this;
        }

        public Builder<T> castAnimation(Function<SpellContext, SpellAnimation> castAnimationName) {
            this.castAnimation = castAnimationName;
            return this;
        }

        public Builder<T> instantCast() {
            this.castAnimation = context -> new SpellAnimation("instant_cast", SpellAnimation.Type.CAST, true);
            this.castTime = 5;
            return this;
        }

        public Builder<T> summonCast() {
            this.castAnimation = context -> new SpellAnimation("summon", SpellAnimation.Type.CAST, true);
            this.castTime = 30;
            return this;
        }

        public Builder<T> selfBuffCast() {
            this.castAnimation = context -> new SpellAnimation("self_buff", SpellAnimation.Type.CAST, true);
            return this;
        }

        public Builder<T> castCondition(BiPredicate<SpellContext, T> castCondition) {
            this.castPredicate = castCondition;
            return this;
        }

        public Builder<T> additionalCondition(BiPredicate<SpellContext, T> castCondition) {
            this.castPredicate = this.castPredicate.and(castCondition);
            return this;
        }

        public Builder<T> castType(CastType castType) {
            this.castType = castType;
            return this;
        }

        public Builder<T> castSound(SoundEvent castSound) {
            this.castSound = castSound;
            return this;
        }

        public Builder<T> fullRecast() {
            this.fullRecast = true;
            return this;
        }
        public Builder<T> skipEndOnRecast(Predicate<SpellContext> skipIf) {
            this.skipEndOnRecast = skipIf;
            return this;
        }

        public Builder<T> skipEndOnRecast() {
            this.skipEndOnRecast = context -> true;
            return this;
        }

        public Builder<T> updateInterval(int updateInterval) {
            this.updateInterval = updateInterval;
            return this;
        }
        public Builder<T> hasLayer() {
            this.hasLayer = true;
            return this;
        }

        public Builder<T> negativeScaling(Predicate<SpellContext> negativeScaling) {
            this.negativeScaling = negativeScaling;
            return this;
        }

        public Builder<T> negativeScaling() {
            this.negativeScaling = context -> true;
            return this;
        }
    }
}
