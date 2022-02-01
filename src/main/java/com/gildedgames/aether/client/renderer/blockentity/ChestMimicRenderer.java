package com.gildedgames.aether.client.renderer.blockentity;

import com.gildedgames.aether.common.entity.tile.ChestMimicBlockEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;

import java.util.Calendar;

public class ChestMimicRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	private final ModelPart singleLid;
	private final ModelPart singleBottom;
	private final ModelPart singleLock;
	private boolean christmasTextures = false;

	public ChestMimicRenderer(BlockEntityRendererProvider.Context context) {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
			this.christmasTextures = true;
		}
		ModelPart root = context.bakeLayer(ModelLayers.CHEST);
		this.singleBottom = root.getChild("bottom");
		this.singleLid = root.getChild("lid");
		this.singleLock = root.getChild("lock");
	}

	@Override
	public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Level level = blockEntityIn.getLevel();
		boolean flag = level != null;
		BlockState blockstate = flag ? blockEntityIn.getBlockState() : AetherBlocks.CHEST_MIMIC.get().defaultBlockState().setValue(ChestMimicBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();
		if (block instanceof ChestMimicBlock chestMimicBlock) {
			poseStack.pushPose();
			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
			poseStack.translate(0.5D, 0.5D, 0.5D);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			poseStack.translate(-0.5D, -0.5D, -0.5D);
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestMimicBlockEntity> combineResult;
			if (flag) {
				combineResult = chestMimicBlock.combine(blockstate, level, blockEntityIn.getBlockPos(), true);
			} else {
				combineResult = DoubleBlockCombiner.Combiner::acceptNone;
			}
			combinedLightIn = combineResult.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
			Material material = this.getMaterial(blockEntityIn);
			VertexConsumer vertexConsumer = material.buffer(bufferIn, RenderType::entityCutout);
			this.renderModels(poseStack, vertexConsumer, this.singleLid, this.singleLock, this.singleBottom, combinedLightIn, combinedOverlayIn);
			poseStack.popPose();
		} else {
			LogManager.getLogger(ChestMimicRenderer.class).warn("Invalid block used with ChestMimicTileEntityRenderer!");
		}
	}

	private void renderModels(PoseStack matrixStackIn, VertexConsumer bufferIn, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, int combinedLightIn, int combinedOverlayIn) {
		chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

	protected Material getMaterial(T tileEntity) {
		return Sheets.chooseMaterial(tileEntity, ChestType.SINGLE, this.christmasTextures);
	}
}
