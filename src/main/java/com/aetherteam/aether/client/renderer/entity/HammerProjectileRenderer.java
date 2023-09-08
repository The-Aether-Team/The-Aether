package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.projectile.weapon.HammerProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HammerProjectileRenderer extends EntityRenderer<HammerProjectile> {
    public static final ResourceLocation KINGBDOGZ_WAVE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/kingbdogz_wave.png");
    public static final ResourceLocation JEB_WAVE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/jeb_wave.png");

    public HammerProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    /**
     * [VANILLA COPY] - {@link net.minecraft.client.renderer.entity.DragonFireballRenderer}.
     */
    @Override
    public void render(HammerProjectile hammer, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(hammer)));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        vertex(consumer, matrix4f, matrix3f, packedLight, 0.0F, 0.0F, 0.0F, 1.0F);
        vertex(consumer, matrix4f, matrix3f, packedLight, 1.0F, 0.0F, 1.0F, 1.0F);
        vertex(consumer, matrix4f, matrix3f, packedLight, 1.0F, 1.0F, 1.0F, 0.0F);
        vertex(consumer, matrix4f, matrix3f, packedLight, 0.0F, 1.0F, 0.0F, 0.0F);
        poseStack.popPose();
        super.render(hammer, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normals, int packedLight, float offsetX, float offsetY, float textureX, float textureY) {
        consumer.vertex(matrix, offsetX - 0.5F, offsetY - 0.25F, 0.0F).color(255, 255, 255, 255).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }

    /**
     * Checks whether the projectile should use the Kingbdogz or Jeb hammer texture.
     * @param hammer The {@link HammerProjectile} entity.
     * @return The texture {@link ResourceLocation}.
     */
    @Override
    public ResourceLocation getTextureLocation(HammerProjectile hammer) {
        return !hammer.getIsJeb() ? KINGBDOGZ_WAVE_TEXTURE : JEB_WAVE_TEXTURE;
    }
}
