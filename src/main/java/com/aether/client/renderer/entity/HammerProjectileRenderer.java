package com.aether.client.renderer.entity;

import com.aether.Aether;
import com.aether.entity.projectile.HammerProjectileEntity;
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

public class HammerProjectileRenderer extends EntityRenderer<HammerProjectileEntity> {
    public static final ResourceLocation NOTCH_WAVE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/notch_wave.png");

    public HammerProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.0F;
    }

    @Override
    public void render(HammerProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(this.renderManager.getCameraOrientation());
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
        IVertexBuilder vertex = bufferIn.getBuffer(RenderType.getEntityCutout(getEntityTexture(entityIn)));
        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
        Matrix4f matrix4f = matrixstack$entry.getMatrix();
        Matrix3f matrix3f = matrixstack$entry.getNormal();
        drawVertex(matrix4f, matrix3f, vertex, -0.5F, -0.25F, 0.0F, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, 0.5F, -0.25F, 0.0F, 0.0F, 1.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, 0.5F, 0.75F, 0.0F, 1.0F, 1.0F, 0, 1, 0, packedLightIn);
        drawVertex(matrix4f, matrix3f, vertex, -0.5F, 0.75F, 0.0F, 1.0F, 0.0F, 0, 1, 0, packedLightIn);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(HammerProjectileEntity entity) {
        return NOTCH_WAVE;
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, int normalX, int normalY, int normalZ, int packedLightIn) {
        vertexBuilder.pos(matrix, offsetX, offsetY, offsetZ).color(255, 255, 255, 255).tex(textureX, textureY).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(normals, (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }
}
