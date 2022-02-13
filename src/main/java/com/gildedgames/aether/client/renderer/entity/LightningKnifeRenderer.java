package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.projectile.weapon.ThrownLightningKnife;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

public class LightningKnifeRenderer extends EntityRenderer<ThrownLightningKnife> {
	public LightningKnifeRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.0F;
	}

	@Override
	public void render(ThrownLightningKnife lightningKnife, float entityYaw, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		Quaternion quaternion = Vector3f.YP.rotationDegrees(lightningKnife.getYRot());
		quaternion.mul(Vector3f.XP.rotationDegrees((-(lightningKnife.xRotO + (lightningKnife.getXRot() - lightningKnife.xRotO) * partialTicks)) - 90.0F));
		quaternion.mul(Vector3f.ZP.rotationDegrees(-135.0F));
		poseStack.mulPose(quaternion);
		Minecraft.getInstance().getItemRenderer().renderStatic(lightningKnife.getItem(), ItemTransforms.TransformType.GUI, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, lightningKnife.getId());
		poseStack.popPose();
		super.render(lightningKnife, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(@Nonnull ThrownLightningKnife lightningKnife) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
