package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.block.TntPresent;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

public class TNTPresentRenderer extends EntityRenderer<TntPresent>
{
    public TNTPresentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(TntPresent present, float entityYaw, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);
        if ((float) present.getFuse() - partialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float) present.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f = Mth.square(f);
            f = Mth.square(f);
            float f1 = 1.0F + f * 0.3F;
            poseStack.scale(f1, f1, f1);
        }
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(AetherBlocks.PRESENT.get().defaultBlockState(), poseStack, buffer, packedLight, present.getFuse() / 5 % 2 == 0);
        poseStack.popPose();
        super.render(present, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TntPresent present) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
