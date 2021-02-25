package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.entity.projectile.AbstractDartEntity;
import com.gildedgames.aether.entity.projectile.EnchantedDartEntity;
import com.gildedgames.aether.entity.projectile.GoldenDartEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class DartRenderer<T extends AbstractDartEntity> extends EntityRenderer<T> {
    public static final ResourceLocation GOLDEN_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/golden_dart.png");
    public static final ResourceLocation ENCHANTED_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/enchanted_dart.png");
    public static final ResourceLocation POISON_DART_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/dart/poison_dart.png");
    public DartRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.0F;
    }

    @Override
    public void render(T dart, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (dart.isInvisible()) {
            return;
        }
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(dart.rotationYaw - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(dart.rotationPitch));
        byte i = 1;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float) (i * 10) / 32.0F;
        float f5 = (float) (5 + i * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float) (5 + i * 10) / 32.0F;
        float f9 = (float) (10 + i * 10) / 32.0F;
        float f10 = 0.05625F;
        float f11 = (float) dart.arrowShake - partialTicks;

        if (f11 > 0.0F) {
            float j = -MathHelper.sin(f11 * 3.0F) * f11;
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(j));
        }

        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(45.0F));
        matrixStackIn.scale(f10, f10, f10);
        matrixStackIn.translate(-4.0F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(dart)));

        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
        Matrix4f matrix4f = matrixstack$entry.getMatrix();
        Matrix3f matrix3f = matrixstack$entry.getNormal();

        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, f6, f8, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, f7, f8, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, f7, f9, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, f6, f9, -1, 0, 0, packedLightIn);

        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, f6, f8, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, f7, f8, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, f6, f8, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, f7, f9, 1, 0, 0, packedLightIn);

        for (int var23 = 0; var23 < 5; ++var23) {
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(72.0F));
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, f2, f4, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, f3, f4, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, f3, f5, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, f2, f5, 0, 1, 0, packedLightIn);
        }

        matrixStackIn.pop();
        super.render(dart, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return entity instanceof GoldenDartEntity ? GOLDEN_DART_TEXTURE : entity instanceof EnchantedDartEntity ? ENCHANTED_DART_TEXTURE : POISON_DART_TEXTURE;
    }

    /**
     * Copied from ArrowRenderer to simplify drawing vertices.
     */
    public void drawVertex(Matrix4f matrix, Matrix3f normals, IVertexBuilder vertexBuilder, int offsetX, int offsetY, int offsetZ, float textureX, float textureY, int normalX, int normalY, int normalZ, int packedLightIn) {
        vertexBuilder.pos(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).color(255, 255, 255, 255).tex(textureX, textureY).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(normals, (float)normalX, (float)normalZ, (float)normalY).endVertex();
    }

}
