package com.ombremoon.spellbound.common.world.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.spellbound.common.world.block.entity.IceSheetBlockEntity;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IceSheetBlock extends BaseEntityBlock {
    public static final MapCodec<IceSheetBlock> CODEC = simpleCodec(IceSheetBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public IceSheetBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, 60);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.removeBlock(pos, false);
    }

    @Override
    protected VoxelShape getShape(BlockState p_152917_, BlockGetter p_152918_, BlockPos p_152919_, CollisionContext p_152920_) {
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(
            BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor level, BlockPos pos, BlockPos pos1
    ) {
        return !blockState.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(blockState, direction, blockState1, level, pos, pos1);
    }

    @Override
    protected boolean canSurvive(BlockState p_152922_, LevelReader p_152923_, BlockPos p_152924_) {
        return !p_152923_.isEmptyBlock(p_152924_.below()) && !p_152923_.getBlockState(p_152924_.below()).is(this);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IceSheetBlockEntity(pos, state);
    }
}
