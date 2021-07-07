package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.projectile.combat.LightningKnifeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class LightningKnifeRenderer extends EntityRenderer<LightningKnifeEntity>
{
	public LightningKnifeRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(LightningKnifeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrix.pushPose();
		Quaternion base = Vector3f.YP.rotationDegrees(entityIn.yRot);
		base.mul(Vector3f.XP.rotationDegrees((-(entityIn.xRotO + (entityIn.xRot - entityIn.xRotO) * partialTicks)) - 90.0F));
		base.mul(Vector3f.ZP.rotationDegrees(-135.0F));
		matrix.mulPose(base);
		Minecraft.getInstance().getItemRenderer().renderStatic(entityIn.getItem(), ItemCameraTransforms.TransformType.GUI, packedLightIn, OverlayTexture.NO_OVERLAY, matrix, bufferIn);
		matrix.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrix, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(LightningKnifeEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS;
	}
}
