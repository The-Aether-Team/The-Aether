package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.block.TntPresent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class TNTPresentRenderer extends EntityRenderer<TntPresent> {
    private final BlockRenderDispatcher blockRenderer;
    public TNTPresentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(TntPresent present, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.5, 0.0);
        if ((float) present.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float) present.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f = Mth.square(f);
            f = Mth.square(f);
            float f1 = 1.0F + f * 0.3F;
            poseStack.scale(f1, f1, f1);
        }
        poseStack.translate(-0.5, -0.5, -0.5);
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, AetherBlocks.PRESENT.get().defaultBlockState(), poseStack, buffer, packedLight, present.getFuse() / 5 % 2 == 0);
        poseStack.popPose();
        super.render(present, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

   
    @Override
    public ResourceLocation getTextureLocation(TntPresent present) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
