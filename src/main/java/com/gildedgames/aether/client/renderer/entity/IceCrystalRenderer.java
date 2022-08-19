package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.model.CrystalModel;
import com.gildedgames.aether.entity.projectile.crystal.AbstractCrystal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class IceCrystalRenderer<T extends AbstractCrystal> extends EntityRenderer<T> {
    private static final ResourceLocation ICE_CRYSTAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/projectile/crystals/ice_ball.png");
    private final CrystalModel<AbstractCrystal> crystal;

    public IceCrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.crystal = new CrystalModel<>(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL));
    }

    @Override
    public void render(@Nonnull T crystal, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.25, 0.0);
        VertexConsumer iVertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(crystal)));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(36.0F));
        this.crystal.crystal1.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(36.0F));
        this.crystal.crystal2.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(36.0F));
        this.crystal.crystal3.render(poseStack, iVertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        super.render(crystal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T crystal) {
        return ICE_CRYSTAL_TEXTURE;
    }
}
