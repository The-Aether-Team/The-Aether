package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.common.entity.projectile.crystal.AbstractCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

public abstract class AbstractCrystalRenderer<T extends AbstractCrystal> extends EntityRenderer<T>
{
    private final CrystalModel<AbstractCrystal> model;

    public AbstractCrystalRenderer(EntityRendererProvider.Context context, CrystalModel<AbstractCrystal> model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(@Nonnull T crystal, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.25, 0.0);
        VertexConsumer iVertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(crystal)));
        float f = (float) crystal.tickCount + partialTicks;
        poseStack.mulPose(Vector3f.XP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal_0.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal_1.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(f * 0.1F * 360.0F));
        this.model.crystal_2.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(crystal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
