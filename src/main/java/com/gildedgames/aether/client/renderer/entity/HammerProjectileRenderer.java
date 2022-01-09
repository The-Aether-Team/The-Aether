package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.entity.projectile.weapon.HammerProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HammerProjectileRenderer extends EntityRenderer<HammerProjectileEntity>
{
    public static final ResourceLocation NOTCH_WAVE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/notch_wave.png");
    public static final ResourceLocation JEB_WAVE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/jeb_wave.png");

    public HammerProjectileRenderer(EntityRendererProvider.Context renderer) {
        super(renderer);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(HammerProjectileEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        VertexConsumer vertex = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 0.0F, 0.0F, 1.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 0.0F, 1.0F, 1.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 1.0F, 1.0F, 0.0F);
        vertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 1.0F, 0.0F, 0.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private static void vertex(VertexConsumer p_229045_0_, Matrix4f matrix, Matrix3f normals, int packedLightIn, float offsetX, float offsetY, float textureX, float textureY) {
        p_229045_0_.vertex(matrix, offsetX - 0.5F, offsetY - 0.25F, 0.0F).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(HammerProjectileEntity entity) {
        return !entity.getIsJeb() ? NOTCH_WAVE_TEXTURE : JEB_WAVE_TEXTURE;
    }
}
