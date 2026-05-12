package com.ombremoon.spellbound.mixin;

import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.magic.acquisition.deception.PuzzleDungeonData;
import com.ombremoon.spellbound.common.magic.api.Imbuement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "blockActionRestricted", at = @At("RETURN"), cancellable = true)
    private void blockActionRestricted(Level level, BlockPos pos, GameType gameMode, CallbackInfoReturnable<Boolean> cir) {
        if (PuzzleDungeonData.isDungeon(level)) {
            cir.setReturnValue(true);
        }
    }
}
