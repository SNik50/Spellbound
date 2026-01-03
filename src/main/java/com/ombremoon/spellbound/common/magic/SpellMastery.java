package com.ombremoon.spellbound.common.magic;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum SpellMastery implements StringRepresentable {
    NOVICE("novice", 0),
    APPRENTICE("apprentice", 20),
    ADEPT("adept", 40),
    EXPERT("expert", 60),
    MASTER("master", 80);

    public static final Codec<SpellMastery> CODEC = StringRepresentable.fromEnum(SpellMastery::values);
    private final String name;
    private final int levelRequirement;

    SpellMastery(String name, int levelRequirement) {
        this.name = name;
        this.levelRequirement = levelRequirement;
    }

    public int getLevelRequirement() {
        return this.levelRequirement;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
