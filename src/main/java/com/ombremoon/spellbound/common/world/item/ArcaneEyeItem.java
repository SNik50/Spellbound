package com.ombremoon.spellbound.common.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ArcaneEyeItem extends Item {

    public ArcaneEyeItem(Properties properties) {
        super(properties.durability(5));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = level.getBlockState(blockPos);
        ItemStack stack = context.getItemInHand();
        if (!level.isClientSide && player != null) {
            if (state.getBlock() instanceof DoorBlock) {
                //Create Illusory Hall Portal

                int i = stack.getDamageValue() + 1;
                if (i >= stack.getMaxDamage()) {
                    stack.setDamageValue(0);
                    player.getCooldowns().addCooldown(this, 12000);
                    return InteractionResult.CONSUME;
                }

                stack.setDamageValue(i);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }
}
