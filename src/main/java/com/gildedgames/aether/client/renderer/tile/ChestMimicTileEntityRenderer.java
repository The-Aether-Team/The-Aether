package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.common.entity.tile.ChestMimicTileEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;

import java.util.Calendar;

public class ChestMimicTileEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
//	private final ModelPart singleLid;
//	private final ModelPart singleBottom;
//	private final ModelPart singleLatch;
//	private final ModelPart rightLid;
//	private final ModelPart rightBottom;
//	private final ModelPart rightLatch;
//	private final ModelPart leftLid;
//	private final ModelPart leftBottom;
//	private final ModelPart leftLatch;
	private boolean isChristmas;

	public ChestMimicTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {

		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
			this.isChristmas = true;
		}

//		this.singleBottom = rendererDispatcherIn.

//		this.singleBottom = new ModelPart(64, 64, 0, 19);
//		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
//		this.singleLid = new ModelPart(64, 64, 0, 0);
//		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
//		this.singleLid.y = 9.0F;
//		this.singleLid.z = 1.0F;
//		this.singleLatch = new ModelPart(64, 64, 0, 0);
//		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
//		this.singleLatch.y = 8.0F;
//		this.rightBottom = new ModelPart(64, 64, 0, 19);
//		this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
//		this.rightLid = new ModelPart(64, 64, 0, 0);
//		this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
//		this.rightLid.y = 9.0F;
//		this.rightLid.z = 1.0F;
//		this.rightLatch = new ModelPart(64, 64, 0, 0);
//		this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
//		this.rightLatch.y = 8.0F;
//		this.leftBottom = new ModelPart(64, 64, 0, 19);
//		this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
//		this.leftLid = new ModelPart(64, 64, 0, 0);
//		this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
//		this.leftLid.y = 9.0F;
//		this.leftLid.z = 1.0F;
//		this.leftLatch = new ModelPart(64, 64, 0, 0);
//		this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
//		this.leftLatch.y = 8.0F;
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
//		Level world = tileEntityIn.getLevel();
//		BlockState blockstate = world != null ? tileEntityIn.getBlockState() : AetherBlocks.CHEST_MIMIC.get().defaultBlockState().setValue(ChestMimicBlock.FACING, Direction.SOUTH);
//		ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
//		Block block = blockstate.getBlock();
//		if (block instanceof ChestMimicBlock) {
//			ChestMimicBlock chestMimicBlock = (ChestMimicBlock) block;
//			matrixStackIn.pushPose();
//			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
//			matrixStackIn.translate(0.5D, 0.5D, 0.5D);
//			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
//			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
//			DoubleBlockCombiner.NeighborCombineResult<? extends ChestMimicTileEntity> icallbackwrapper;
//			if (world != null) {
//				icallbackwrapper = chestMimicBlock.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
//			} else {
//				icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
//			}
//			combinedLightIn = icallbackwrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
//			Material rendermaterial = this.getMaterial(tileEntityIn, chesttype);
//			VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutout);
//			if (chesttype != ChestType.SINGLE) {
//				if (chesttype == ChestType.LEFT) {
//					this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, combinedLightIn, combinedOverlayIn);
//				} else {
//					this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, combinedLightIn, combinedOverlayIn);
//				}
//			} else {
//				this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, combinedLightIn, combinedOverlayIn);
//			}
//			matrixStackIn.popPose();
//		} else {
//			LogManager.getLogger(ChestMimicTileEntityRenderer.class).warn("Invalid block used with ChestMimicTileEntityRenderer!");
//		}
	}

	private void renderModels(PoseStack matrixStackIn, VertexConsumer bufferIn, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, int combinedLightIn, int combinedOverlayIn) {
		chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

	protected Material getMaterial(T tileEntity, ChestType chestType) {
		return Sheets.chooseMaterial(tileEntity, chestType, this.isChristmas);
	}
}
