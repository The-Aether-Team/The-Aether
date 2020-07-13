package com.aether.client.renderer.entity;

import java.util.Random;

import com.aether.entity.item.FloatingBlockEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FloatingBlockRenderer extends EntityRenderer<FloatingBlockEntity> {

	public FloatingBlockRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5f;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(FloatingBlockEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		BlockState blockstate = entityIn.getBlockState();
	      if (blockstate.getRenderType() == BlockRenderType.MODEL) {
	         World world = entityIn.getWorldObj();
	         if (blockstate != world.getBlockState(new BlockPos(entityIn)) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
	            matrixStackIn.push();
	            BlockPos blockpos = new BlockPos(entityIn.getPosX(), entityIn.getBoundingBox().maxY, entityIn.getPosZ());
	            matrixStackIn.translate(-0.5, 0.0, -0.5);
	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
	            for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.getBlockRenderTypes()) {
	               if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
	                  net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
	                  blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, matrixStackIn, bufferIn.getBuffer(type), false, new Random(), blockstate.getPositionRandom(entityIn.getOrigin()), OverlayTexture.NO_OVERLAY);
	               }
	            }
	            net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
	            matrixStackIn.pop();
	            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	         }
	      }
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getEntityTexture(FloatingBlockEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

}
