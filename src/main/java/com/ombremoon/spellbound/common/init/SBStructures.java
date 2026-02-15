package com.ombremoon.spellbound.common.init;

import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.world.worldgen.structure.NonRotatingJigsawStructure;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SBStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Constants.MOD_ID);

    public static final Supplier<StructureType<NonRotatingJigsawStructure>> NON_ROTATING_JIGSAW = STRUCTURES.register("non_rotating_jigsaw", () -> structureType(NonRotatingJigsawStructure.CODEC));

    private static <T extends Structure> StructureType<T> structureType(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register(IEventBus modEventBus) {
        STRUCTURES.register(modEventBus);
    }
}
