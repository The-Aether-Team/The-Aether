package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import com.aetherteam.aether.client.renderer.entity.state.CrystalRenderState;
import com.aetherteam.aether.entity.projectile.crystal.AbstractCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractCrystalRenderer<T extends AbstractCrystal, R extends CrystalRenderState> extends EntityRenderer<T, R> {
    private final CrystalModel<CrystalRenderState> crystal;

    public AbstractCrystalRenderer(EntityRendererProvider.Context context, CrystalModel<CrystalRenderState> crystalModel) {
        super(context);
        this.crystal = crystalModel;
    }

    @Override
    public void extractRenderState(T p_entity, R reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = p_entity.getYRot(partialTick);
    }

    /**
     * Rotates the different parts of the crystal model.
     *
     * @param renderState      The {@link EntityRenderState}.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param buffer       The rendering {@link MultiBufferSource}.
     * @param packedLight  The {@link Integer} for the packed lighting for rendering.
     */
    @Override
    public void render(R renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.4, 0.0);
        VertexConsumer iVertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(renderState)));
        float f = renderState.ageInTicks + renderState.partialTick;
        poseStack.mulPose(Axis.XP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal1.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal2.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 0.1F * 360.0F));
        this.crystal.crystal3.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, buffer, packedLight);
    }

    protected abstract ResourceLocation getTextureLocation(R crystal);
}
