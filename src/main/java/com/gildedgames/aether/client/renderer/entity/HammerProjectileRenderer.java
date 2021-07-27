package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
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

public class HammerProjectileRenderer extends EntityRenderer<HammerProjectileEntity>
{
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Aether.MODID, "textures/entity/projectile/notch_wave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

    public HammerProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(HammerProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        IVertexBuilder vertex = bufferIn.getBuffer(RENDER_TYPE);
        MatrixStack.Entry matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 0.0F, 0.0F, 1.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 0.0F, 1.0F, 1.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 1.0F, 1.0F, 0.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 1.0F, 0.0F, 0.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private static void vertex(IVertexBuilder p_229045_0_, Matrix4f matrix, Matrix3f normals, int packedLightIn, float offsetX, float offsetY, float textureX, float textureY) {
        p_229045_0_.vertex(matrix, offsetX - 0.5F, offsetY - 0.25F, 0.0F).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(HammerProjectileEntity entity) {
        return TEXTURE_LOCATION;
    }
}
