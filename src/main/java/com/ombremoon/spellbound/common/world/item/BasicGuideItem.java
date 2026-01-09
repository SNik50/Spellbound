package com.ombremoon.spellbound.common.world.item;

import com.ombremoon.spellbound.client.gui.BasicGuideScreen;
import com.ombremoon.spellbound.client.gui.GuideBookScreen;
import com.ombremoon.spellbound.common.magic.acquisition.guides.GuideBookManager;
import com.ombremoon.spellbound.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BasicGuideItem extends Item {

    public BasicGuideItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            RenderUtil.openBasicBook();
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));

    }
}
