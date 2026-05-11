package com.ombremoon.spellbound.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.ombremoon.spellbound.client.ImbuementRenderers;
import com.ombremoon.spellbound.common.init.SBData;
import com.ombremoon.spellbound.common.magic.api.Imbuement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ResourceManagerReloadListener {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getFoilBufferDirect(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer overlayImbuementGlint(MultiBufferSource bufferSource, RenderType renderType, boolean noEntity, boolean withGlint, ItemStack stack, ItemDisplayContext context) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            Imbuement imbuement = stack.get(SBData.IMBUEMENT);
            if (imbuement != null) {
                int endTick = imbuement.endTick();
                if (endTick > 0 && endTick > player.tickCount) {
                    return getEffectFoilBufferDirect(stack, bufferSource, renderType, true, true);
                }
            }
        }
        return ItemRenderer.getFoilBufferDirect(bufferSource, renderType, noEntity, withGlint);
    }

    private static VertexConsumer getEffectFoilBufferDirect(ItemStack item, MultiBufferSource mbs, RenderType p_115224_, boolean p_115225_, boolean hasFoil) {
        Imbuement imbuement = item.get(SBData.IMBUEMENT);
        VertexConsumer consumer = mbs.getBuffer(p_115224_);
        if (imbuement == null) {
            consumer = hasFoil ? VertexMultiConsumer.create(mbs.getBuffer(p_115225_ ? ImbuementRenderers.getSmiteGlintDirect() : ImbuementRenderers.getSmiteEntityGlintDirect()), mbs.getBuffer(p_115224_)) : consumer;
        }

        return consumer;
    }
}
