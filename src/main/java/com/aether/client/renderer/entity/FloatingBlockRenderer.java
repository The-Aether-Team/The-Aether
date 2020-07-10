package com.aether.client.renderer.entity;

import java.util.Random;

import com.aether.entity.item.FloatingBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
 
	@Override
	public boolean shouldRender(FloatingBlockEntity livingEntity, ICamera camera, double camX, double camY, double camZ) {
		return super.shouldRender(livingEntity, camera, camX, camY, camZ);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void doRender(FloatingBlockEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		BlockState blockstate = entity.getBlockState();
		if (blockstate.getRenderType() == BlockRenderType.MODEL) {
			World world = entity.getWorldObj();
			if (blockstate != world.getBlockState(new BlockPos(entity)) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
				this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.pushMatrix();
				GlStateManager.disableLighting();
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				if (this.renderOutlines) {
					GlStateManager.enableColorMaterial();
					GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
				}

				bufferbuilder.begin(7, DefaultVertexFormats.BLOCK);
				BlockPos blockpos = new BlockPos(entity.posX, entity.getBoundingBox().maxY, entity.posZ);
				GlStateManager.translatef((float) (x - blockpos.getX() - 0.5D), (float) (y - blockpos.getY()), (float) (z - blockpos.getZ() - 0.5D));
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, bufferbuilder, false, new Random(), blockstate.getPositionRandom(entity.getOrigin()));
				tessellator.draw();
				if (this.renderOutlines) {
					GlStateManager.tearDownSolidRenderingTextureCombine();
					GlStateManager.disableColorMaterial();
				}

				GlStateManager.enableLighting();
				GlStateManager.popMatrix();
				super.doRender(entity, x, y, z, entityYaw, partialTicks);
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(FloatingBlockEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

}
