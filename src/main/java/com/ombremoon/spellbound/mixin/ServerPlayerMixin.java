package com.ombremoon.spellbound.mixin;

import com.mojang.authlib.GameProfile;
import com.ombremoon.spellbound.common.init.SBTriggers;
import com.ombremoon.spellbound.main.Constants;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin extends Player {

    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "setPlayerInput", at = @At("TAIL"))
    private void setPlayerInput(float strafe, float forward, boolean jumping, boolean sneaking, CallbackInfo ci) {
/*        var handler = SpellUtil.getSpellHandler(this);
        if (handler.inCastMode()) {
            if (strafe >= -1.0F && strafe <= 1.0F) {
                this.xxa = strafe;
            }

            if (forward >= -1.0F && forward <= 1.0F) {
                this.zza = forward;
            }

            this.jumping = jumping;
            this.setShiftKeyDown(sneaking);
        }*/
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
