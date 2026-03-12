package com.ombremoon.spellbound.common.world.item;

import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SpiritWhistleItem extends Item {
    public SpiritWhistleItem(Properties properties) {
        super(properties.durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        summon(player, player.blockPosition());
        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        summon(context.getPlayer(), context.getClickedPos());
        return super.useOn(context);
    }

    private void summon(Player player, BlockPos pos) {
        var handler = SpellUtil.getFamiliarHandler(player);
        if (handler.hasActiveFamiliar()) {
            handler.discardFamiliar();
        }

        handler.summonFamiliar(pos);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }
}
