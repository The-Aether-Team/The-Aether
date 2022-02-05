package com.gildedgames.aether.client.renderer.entity;

import java.util.Random;

import com.gildedgames.aether.common.entity.block.FloatingBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class FloatingBlockRenderer extends EntityRenderer<FloatingBlockEntity>
{
	public FloatingBlockRenderer(EntityRendererProvider.Context renderer) {
		super(renderer);
		this.shadowRadius = 0.5F;
	}

	@Override
	public void render(FloatingBlockEntity floatingBlock, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLightIn) {
		BlockState blockstate = floatingBlock.getBlockState();
		if (blockstate.getRenderShape() == RenderShape.MODEL) {
			Level world = floatingBlock.getLevel();
			if (blockstate != world.getBlockState(floatingBlock.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				poseStack.pushPose();
				BlockPos blockPos = new BlockPos(floatingBlock.getX(), floatingBlock.getBoundingBox().maxY, floatingBlock.getZ());
				poseStack.translate(-0.5D, 0.0D, -0.5D);
				BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
				for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.chunkBufferLayers()) {
					if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
						net.minecraftforge.client.ForgeHooksClient.setRenderType(type);
						blockRenderDispatcher.getModelRenderer().tesselateBlock(world, blockRenderDispatcher.getBlockModel(blockstate), blockstate, blockPos, poseStack, buffer.getBuffer(type), false, new Random(), blockstate.getSeed(floatingBlock.getStartPos()), OverlayTexture.NO_OVERLAY);
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderType(null);
				poseStack.popPose();
				super.render(floatingBlock, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
			}
		}
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(@Nonnull FloatingBlockEntity floatingBlock) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}
