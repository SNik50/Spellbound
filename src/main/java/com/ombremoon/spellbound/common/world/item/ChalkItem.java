package com.ombremoon.spellbound.common.world.item;

import com.ombremoon.spellbound.common.world.block.RuneBlock;
import com.ombremoon.spellbound.common.world.block.entity.RuneBlockEntity;
import com.ombremoon.spellbound.common.init.SBBlocks;
import com.ombremoon.spellbound.common.init.SBData;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class ChalkItem extends BlockItem {

    public ChalkItem(Properties properties) {
        super(SBBlocks.RUNE.get(), properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (super.place(context) != InteractionResult.FAIL) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            ItemStack stack = context.getItemInHand();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof RuneBlockEntity runeBlock) {
                DyedItemColor color = stack.get(DataComponents.DYED_COLOR);
                if (color != null) {
                    runeBlock.setData(SBData.RUNE_COLOR, color.rgb());
                }
            }
        }
        return super.place(context);
    }

    @Override
    public Block getBlock() {
        return SBBlocks.RUNE.get();
    }

    @Override
    protected @Nullable BlockState getPlacementState(BlockPlaceContext context) {
        ItemStack stack = context.getItemInHand();
        int type = this.getRuneType(stack);
        BlockState blockState = this.getBlock().defaultBlockState().setValue(RuneBlock.RUNE_TYPE, type);
        return this.canPlace(context, blockState) ? blockState : null;
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    private int getRuneType(ItemStack stack) {
        int[] runes = getOrCreateRunes(stack);
        var type = stack.get(SBData.RUNE_INDEX);

        if (type == null || type >= 26) {
            shuffle(runes);
            type = 0;
        }

        int rune = runes[type];
        stack.set(SBData.RUNE_INDEX, type + 1);
        return rune;
    }

    private static int[] getOrCreateRunes(ItemStack stack) {
        var list = stack.get(SBData.RUNES);
        if (list == null) {
            int[] newRunes = IntStream.rangeClosed(1, 26).toArray();
            shuffle(newRunes);
            stack.set(SBData.RUNES, new IntArrayList(newRunes));
            return newRunes;
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void shuffle(int[] array) {
        RandomSource random = RandomSource.create();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
