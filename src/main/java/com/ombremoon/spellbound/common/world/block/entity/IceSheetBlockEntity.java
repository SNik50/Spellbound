package com.ombremoon.spellbound.common.world.block.entity;

import com.ombremoon.spellbound.common.init.SBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IceSheetBlockEntity extends BlockEntity {
    public IceSheetBlockEntity(BlockPos pos, BlockState blockState) {
        super(SBBlockEntities.ICE_SHEET.get(), pos, blockState);
    }
}
