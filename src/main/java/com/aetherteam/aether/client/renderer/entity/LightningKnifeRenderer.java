package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

import javax.annotation.Nonnull;

public class LightningKnifeRenderer extends EntityRenderer<ThrownLightningKnife> {
	public LightningKnifeRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.0F;
	}

	@Override
	public void render(ThrownLightningKnife lightningKnife, float entityYaw, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		Quaternionf quaternion = Axis.YP.rotationDegrees(lightningKnife.getYRot());
		quaternion.mul(Axis.XP.rotationDegrees((-(lightningKnife.xRotO + (lightningKnife.getXRot() - lightningKnife.xRotO) * partialTicks)) - 90.0F));
		quaternion.mul(Axis.ZP.rotationDegrees(-135.0F));
		poseStack.mulPose(quaternion);
		Minecraft.getInstance().getItemRenderer().renderStatic(lightningKnife.getItem(), ItemDisplayContext.GUI, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, lightningKnife.level, lightningKnife.getId());
		poseStack.popPose();
		super.render(lightningKnife, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(@Nonnull ThrownLightningKnife lightningKnife) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
