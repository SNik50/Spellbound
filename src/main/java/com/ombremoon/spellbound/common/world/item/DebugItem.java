package com.ombremoon.spellbound.common.world.item;

import com.ombremoon.spellbound.common.init.*;
import com.ombremoon.spellbound.common.magic.EffectManager;
import com.ombremoon.spellbound.common.magic.SpellHandler;
import com.ombremoon.spellbound.common.magic.skills.SkillHolder;
import com.ombremoon.spellbound.common.world.entity.projectile.MushroomProjectile;
import com.ombremoon.spellbound.common.world.spell.ruin.shock.StormRiftSpell;
import com.ombremoon.spellbound.main.Constants;
import com.ombremoon.spellbound.main.Keys;
import com.ombremoon.spellbound.networking.PayloadHandler;
import com.ombremoon.spellbound.util.Loggable;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class DebugItem extends Item implements Loggable {
    public DebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var handler = SpellUtil.getSpellHandler(player);
        var skillHandler = SpellUtil.getSkills(player);
        duckDebug(level, player, usedHand, handler, skillHandler);
        //ombreDebug(level, player, usedHand, handler, skillHandler);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player player = context.getPlayer();
        if (!level.isClientSide) {
            log(level.getBlockState(blockPos).getShape(level, blockPos).max(Direction.Axis.Y));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void ombreDebug(Level level, Player player, InteractionHand usedHand, SpellHandler spellHandler, SkillHolder skillHolder) {
        log(level.registryAccess().registryOrThrow(Keys.RITUAL).get(SBRituals.CREATE_MYSTIC_ARMOR).definition());
    }

    private void duckDebug(Level level, Player player, InteractionHand hand, SpellHandler spellHandler, SkillHolder skillHolder) {
        EffectManager manager = SpellUtil.getSpellEffects(player);
        manager.incrementRuinEffects(SBDamageTypes.RUIN_SHOCK, 100);
    }
}
