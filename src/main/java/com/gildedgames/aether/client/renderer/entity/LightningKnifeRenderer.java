package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.projectile.weapon.LightningKnifeEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightningKnifeRenderer extends EntityRenderer<LightningKnifeEntity>
{
	public LightningKnifeRenderer(EntityRenderDispatcher renderManager) {
		super(renderManager);
		this.shadowRadius = 0.0F;
	}

	@Override
	public void render(LightningKnifeEntity entityIn, float entityYaw, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int packedLightIn) {
		matrix.pushPose();
		Quaternion base = Vector3f.YP.rotationDegrees(entityIn.yRot);
		base.mul(Vector3f.XP.rotationDegrees((-(entityIn.xRotO + (entityIn.xRot - entityIn.xRotO) * partialTicks)) - 90.0F));
		base.mul(Vector3f.ZP.rotationDegrees(-135.0F));
		matrix.mulPose(base);
		Minecraft.getInstance().getItemRenderer().renderStatic(entityIn.getItem(), ItemTransforms.TransformType.GUI, packedLightIn, OverlayTexture.NO_OVERLAY, matrix, bufferIn);
		matrix.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrix, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(LightningKnifeEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
