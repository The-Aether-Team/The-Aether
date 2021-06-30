package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ParachuteRenderer extends EntityRenderer<ParachuteEntity>
{
    public ParachuteRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(ParachuteEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.5, 0.0, 0.5);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(entityIn.getParachuteType().getAercloudBlock().defaultBlockState(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ParachuteEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS;
    }
}
