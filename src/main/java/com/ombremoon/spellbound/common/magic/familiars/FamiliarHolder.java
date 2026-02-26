package com.ombremoon.spellbound.common.magic.familiars;

import com.ombremoon.spellbound.common.magic.SpellMastery;
import com.ombremoon.spellbound.common.magic.skills.FamiliarAffinity;
import com.ombremoon.spellbound.common.world.entity.living.familiars.SBFamiliarEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FamiliarHolder<T extends LivingEntity, F extends Familiar<T>> {
    private final ResourceLocation identifier;
    private final Supplier<EntityType<T>> entity;
    private final SpellMastery mastery;
    private final Type type;
    private final int maxLevel;
    private final FamiliarBuilder<F> builder;
    private final List<FamiliarAffinity> affinities;

    public FamiliarHolder(ResourceLocation identifier, Supplier<EntityType<T>> entity, SpellMastery mastery, Type type, FamiliarBuilder<F> builder, FamiliarAffinity... affinities) {
        this(identifier, entity, mastery, type, builder, 5, affinities);
    }

    public FamiliarHolder(ResourceLocation identifier, Supplier<EntityType<T>> entity, SpellMastery mastery, Type type, FamiliarBuilder<F> builder, int maxLevel, FamiliarAffinity... affinities) {
        this.identifier = identifier;
        this.type = type;
        this.entity = entity;
        this.mastery = mastery;
        this.maxLevel = maxLevel;
        this.builder = builder;
        this.affinities = Arrays.stream(affinities).toList();
        FamiliarHandler.registerFamiliarMastery(this, mastery);
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public SpellMastery getMastery() {
        return mastery;
    }

    public EntityType<T> getEntity() {
        return entity.get();
    }

    public FamiliarBuilder<F> getBuilder() {
        return builder;
    }

    public List<FamiliarAffinity> getAffinities() {
        return affinities;
    }

    public Type getType() {
        return type;
    }

    @FunctionalInterface
    public interface FamiliarBuilder<F extends Familiar<?>> {
        F create(int bond, int rebirths);
    }

    public enum Type {
        UTIL("utility"),
        HYBRID("hybrid"),
        COMBAT("combat");

        private Component name;

        Type(String name) {
            this.name = Component.translatable("spellbound.familiar_type." + name);
        }

        public Component getName() {
            return name;
        }
    }
}
