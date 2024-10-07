package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

public abstract class AbstractCrystalRenderer<T extends AbstractCrystal> extends EntityRenderer<T> {
    private final CrystalModel<AbstractCrystal> crystal;

    public AbstractCrystalRenderer(EntityRendererProvider.Context context, CrystalModel<AbstractCrystal> crystalModel) {
        super(context);
        this.crystal = crystalModel;
    }

    /**
     * Rotates the different parts of the crystal model.
     *
     * @param crystal      The entity.
     * @param entityYaw    The {@link Float} for the entity's yaw rotation.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param buffer       The rendering {@link MultiBufferSource}.
     * @param packedLight  The {@link Integer} for the packed lighting for rendering.
     */
    @Override
    public void render(T crystal, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.4, 0.0);
        VertexConsumer iVertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(crystal)));
        float f = (float) crystal.tickCount + partialTicks;
        poseStack.mulPose(Axis.XP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal1.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal2.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal3.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(crystal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
