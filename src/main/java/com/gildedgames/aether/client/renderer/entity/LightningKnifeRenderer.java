package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.projectile.LightningKnifeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Objects;

public class LightningKnifeRenderer extends EntityRenderer<LightningKnifeEntity> {

	public LightningKnifeRenderer(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(LightningKnifeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrix.pushPose();
		//matrixStackIn.scale(1.0F, 1.0F, 1.0F);
		
		
		Quaternion base;
		
		base = Vector3f.YP.rotationDegrees(entityIn.yRot);
		base.mul(Vector3f.XP.rotationDegrees((-(entityIn.xRotO + (entityIn.xRot - entityIn.xRotO) * partialTicks))-90.0f));
		base.mul(Vector3f.ZP.rotationDegrees(-135.0f));

		matrix.mulPose(base);
		
//		matrix.rotate(Vector3f.YP.rotationDegrees(entityIn.rotationYaw));
//		matrix.rotate(Vector3f.XP.rotationDegrees(-(entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks)));
//		matrix.rotate(Vector3f.YP.rotationDegrees(45.0F));

//		Quaternion base;
//		
//		base = Vector3f.YP.rotationDegrees(entityYaw);
//		float entityYawRadians = entityYaw * (float)(Math.PI / 180.0F);
//		Vector3f temp = Vector3f.ZP.copy();
//		temp.transform(base);
//		base.multiply(temp.rotationDegrees(45.0F));
//		
//		Quaternion temp = Vector3f.XP.rotationDegrees(90.0F);
//		temp.conjugate();
//		base.multiply(temp);
//		base.conjugate();
		
//		base.multiply();
		
//		matrix.rotate(base);
		
		
		
//		matrix.rotate(Vector3f.XP.rotationDegrees(90.0F));
//		matrix.rotate(Vector3f.ZP.rotationDegrees(45.0F));
//		matrix.rotate(Vector3f.YP.rotationDegrees(entityYaw));
		
		Minecraft.getInstance().getItemRenderer().renderStatic(Objects.requireNonNull(((IRendersAsItem)entityIn).getItem()), ItemCameraTransforms.TransformType.GUI, packedLightIn, OverlayTexture.NO_OVERLAY, matrix, bufferIn);
		matrix.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrix, bufferIn, packedLightIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getTextureLocation(LightningKnifeEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS;
	}

}
