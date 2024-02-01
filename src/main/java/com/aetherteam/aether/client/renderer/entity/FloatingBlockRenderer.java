package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class FloatingBlockRenderer extends EntityRenderer<FloatingBlockEntity> {
	public FloatingBlockRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
	}

	@Override
	public void render(FloatingBlockEntity floatingBlock, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
		BlockState blockState = floatingBlock.getBlockState();
		if (blockState.getRenderShape() == RenderShape.MODEL) {
			Level world = floatingBlock.level();
			if (blockState != world.getBlockState(floatingBlock.blockPosition()) && blockState.getRenderShape() != RenderShape.INVISIBLE) {
				poseStack.pushPose();
				BlockPos blockPos = BlockPos.containing(floatingBlock.getX(), floatingBlock.getBoundingBox().maxY, floatingBlock.getZ());
				poseStack.translate(-0.5, 0.0, -0.5);
				BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
				BakedModel model = blockRenderDispatcher.getBlockModel(blockState);
				for (RenderType renderType : model.getRenderTypes(blockState, RandomSource.create(blockState.getSeed(floatingBlock.getStartPos())), ModelData.EMPTY)) {
					blockRenderDispatcher.getModelRenderer().tesselateBlock(world, model, blockState, blockPos, poseStack, buffer.getBuffer(renderType), false, RandomSource.create(), blockState.getSeed(floatingBlock.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
				}
				poseStack.popPose();
				super.render(floatingBlock, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(FloatingBlockEntity floatingBlock) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
