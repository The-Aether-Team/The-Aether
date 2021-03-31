package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.HammerProjectileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HammerProjectileRenderer extends EntityRenderer<HammerProjectileEntity> {
    public static final ResourceLocation NOTCH_WAVE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/notch_wave.png");

    public HammerProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowRadius = 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(HammerProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        IVertexBuilder vertex = bufferIn.getBuffer(RenderType.entityCutout(getTextureLocation(entityIn)));
        MatrixStack.Entry matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, vertex, -0.5F, -0.25F, 0.0F, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, 0.5F, -0.25F, 0.0F, 0.0F, 1.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, 0.5F, 0.75F, 0.0F, 1.0F, 1.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, -0.5F, 0.75F, 0.0F, 1.0F, 0.0F, 0, 1, 0, packedLightIn);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(HammerProjectileEntity entity) {
        return NOTCH_WAVE;
    }

    @OnlyIn(Dist.CLIENT)
    public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, int normalX, int normalY, int normalZ, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }
}
