package com.ombremoon.spellbound.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ombremoon.spellbound.common.world.block.entity.DarkAltarBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DarkAltarRenderer implements BlockEntityRenderer<DarkAltarBlockEntity> {
    private final ItemRenderer itemRenderer;

    public DarkAltarRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(DarkAltarBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Direction direction = Direction.NORTH;
        NonNullList<ItemStack> nonnulllist = blockEntity.getItems();
        int i = (int)blockEntity.getBlockPos().asLong();

        for (int j = 0; j < nonnulllist.size(); j++) {
            ItemStack itemstack = nonnulllist.get(j);
            if (itemstack != ItemStack.EMPTY) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 1.01F, 0.5F);
                Direction direction1 = Direction.from2DDataValue((j + direction.get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(-0.3125F, -0.3125F, 0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                int k = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().relative(direction));
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, k, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), i + j);
                poseStack.popPose();
            }
        }

        if (blockEntity.hasChalk()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.0F, 0.5F);
            float f = (float) blockEntity.chalkTick + partialTick;
            poseStack.translate(0.0F, 0.1F + Mth.sin(f * 0.1F) * 0.01F, 0.0F);
            float f1 = blockEntity.rot - blockEntity.oRot;

            while (f1 >= (float) Math.PI) {
                f1 -= (float) (Math.PI * 2);
            }

            while (f1 < (float) -Math.PI) {
                f1 += (float) (Math.PI * 2);
            }

            float f2 = blockEntity.oRot + f1 * partialTick;
            poseStack.mulPose(Axis.YP.rotation(-f2));
            int k = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().above());
            this.itemRenderer.renderStatic(blockEntity.chalk, ItemDisplayContext.GROUND, k, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
