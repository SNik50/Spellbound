package com.ombremoon.spellbound.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.spellbound.client.shader.SBShaders;
import com.ombremoon.spellbound.util.SpellUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "resize", at = @At("HEAD"))
    private void resize(int width, int height, CallbackInfo info) {
        SBShaders.resize(width, height);
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void disableBobWhileCasting(PoseStack poseStack, float partialTicks, CallbackInfo ci) {
        if (this.minecraft.getCameraEntity() instanceof Player player) {
            var handler = SpellUtil.getSpellHandler(player);
            if (handler.isStationary())
                ci.cancel();
        }
    }
}
