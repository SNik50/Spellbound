package com.ombremoon.spellbound.common.magic.acquisition.deception;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ombremoon.spellbound.common.magic.acquisition.bosses.DynamicLevelSpawnData;
import com.ombremoon.spellbound.common.magic.acquisition.divine.SpellAction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record PuzzleDefinition(
        ResourceLocation puzzleId,
        List<SpellAction> objectives,
        List<ResourceLocation> rules,
        List<SpellAction> resetConditions,
        int maxResetCount,
        DynamicLevelSpawnData spawnData
) {
    public static final Codec<PuzzleDefinition> CODEC = RecordCodecBuilder.<PuzzleDefinition>create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("puzzle_id").forGetter(PuzzleDefinition::puzzleId),
                    SpellAction.CODEC.listOf().fieldOf("objectives").forGetter(PuzzleDefinition::objectives),
                    ResourceLocation.CODEC.listOf().fieldOf("rules").forGetter(PuzzleDefinition::rules),
                    SpellAction.CODEC.listOf().fieldOf("reset_conditions").forGetter(PuzzleDefinition::resetConditions),
                    Codec.INT.fieldOf("max_reset_count").forGetter(PuzzleDefinition::maxResetCount),
                    DynamicLevelSpawnData.CODEC.fieldOf("spawn_data").forGetter(PuzzleDefinition::spawnData)
            ).apply(instance, PuzzleDefinition::new)
    ).validate(PuzzleDefinition::validate);

    private static DataResult<PuzzleDefinition> validate(PuzzleDefinition properties) {
        for (ResourceLocation rule : properties.rules()) {
            if (!DungeonRules.isRule(rule))
                return DataResult.error(() -> "Invalid rule: " + rule);
        }

        return DataResult.success(properties);
    }

    public List<SpellAction> getObjectives() {
        return objectives;
    }

    public boolean hasRule(ResourceLocation rule) {
        return rules.contains(rule);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof PuzzleDefinition definition && this.puzzleId.equals(definition.puzzleId);
        }
    }

    @Override
    public int hashCode() {
        return this.puzzleId.hashCode();
    }

    public static class Builder {
        private ResourceLocation puzzleId;
        private final List<SpellAction> objectives = new ArrayList<>();
        private final List<ResourceLocation> rules = new ArrayList<>();
        private final List<SpellAction> resetConditions = new ArrayList<>();
        private int maxResetCount = 3;
        private DynamicLevelSpawnData spawnData = new DynamicLevelSpawnData(Vec3.ZERO, 0f, Vec3.ZERO);

        public static Builder define(ResourceLocation puzzleId) {
            Builder builder = new Builder();
            builder.puzzleId = puzzleId;
            return builder;
        }

        public Builder withObjective(SpellAction.Builder action) {
            this.objectives.add(action.build());
            return this;
        }

        public Builder addRule(ResourceLocation rule) {
            this.rules.add(rule);
            return this;
        }

        public Builder resetOn(SpellAction.Builder action) {
            this.resetConditions.add(action.build());
            return this;
        }

        public Builder maxResetCount(int count) {
            this.maxResetCount = count;
            return this;
        }

        public Builder spawnData(DynamicLevelSpawnData.Builder spawnData) {
            this.spawnData = spawnData.build();
            return this;
        }

        public PuzzleDefinition build() {
            return new PuzzleDefinition(puzzleId, objectives, rules, resetConditions, maxResetCount, this.spawnData);
        }
    }
}
