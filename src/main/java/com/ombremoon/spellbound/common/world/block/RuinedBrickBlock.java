package com.ombremoon.spellbound.common.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RuinedBrickBlock extends Block {
    public static final EnumProperty<RuinPaths> RUIN_PATH = EnumProperty.create("ruin_path", RuinPaths.class);

    public RuinedBrickBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(RUIN_PATH, RuinPaths.NONE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        state = state.cycle(RUIN_PATH);
        level.setBlock(pos, state, 3);
        return InteractionResult.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RUIN_PATH);
    }

    public enum RuinPaths implements StringRepresentable {
        NONE("none"),
        FIRE("fire"),
        FROST("frost"),
        SHOCK("shock"),
        EARTH("earth"),
        AIR("air"),
        WATER("water");

        private final String name;

        RuinPaths(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
