package com.ombremoon.spellbound.common.world.multiblock;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public abstract class TransfigurationMultiblockPart extends BlockEntity implements MultiblockPart {
    protected static final Logger LOGGER = Constants.LOG;
    private Multiblock multiblock;
    private MultiblockIndex index;
    private Direction facing;
    private int startupTime;

    public TransfigurationMultiblockPart(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public Multiblock getMultiblock() {
        return this.multiblock;
    }

    @Override
    public MultiblockIndex getIndex() {
        return this.index;
    }

    @Override
    public void assign(Multiblock multiblock, MultiblockIndex index, Direction facing) {
        this.multiblock = multiblock;
        this.index = index;
        this.facing = facing;
    }

    @Override
    public Direction getPartDirection() {
        return this.facing;
    }

    @Override
    public boolean isAssigned() {
        return this.multiblock != null;
    }

    public int getRitualStartTicks() {
        return this.startupTime;
    }

    public void setStartupTime(int startupTime) {
        this.startupTime = startupTime;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.save(tag);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.load(tag);
    }

    @Override
    public void save(CompoundTag tag) {
        if (this.multiblock != null) {
            Multiblock.CODEC
                    .encodeStart(NbtOps.INSTANCE, this.multiblock)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(nbt -> tag.put("Multiblock", nbt));
        }

        if (this.index != null) {
            MultiblockIndex.CODEC
                    .encodeStart(NbtOps.INSTANCE, this.index)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(nbt -> tag.put("Index", nbt));
        }

        if (this.facing != null) {
            Direction.CODEC
                    .encodeStart(NbtOps.INSTANCE, this.facing)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(nbt -> tag.put("Direction", nbt));
        }
        tag.putInt("StartupTime", this.startupTime);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Multiblock", 10)) {
            DataResult<Multiblock> dataResult = Multiblock.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Multiblock")));
            dataResult.resultOrPartial(LOGGER::error).ifPresent(block -> this.multiblock = block);
        } else {
            this.multiblock = null;
        }

        if (tag.contains("Index", 10)) {
            DataResult<MultiblockIndex> dataResult = MultiblockIndex.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Index")));
            dataResult.resultOrPartial(LOGGER::error).ifPresent(index -> this.index = index);
        }
        if (tag.contains("Direction", 10)) {
            DataResult<Direction> dataResult = Direction.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Direction")));
            dataResult.resultOrPartial(LOGGER::error).ifPresent(direction -> this.facing = direction);
        }
        this.startupTime = tag.getInt("StartupTime");
    }
}
