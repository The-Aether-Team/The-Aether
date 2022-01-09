package com.gildedgames.aether.client.renderer.tile;

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

public class ChestMimicBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>
{
	private final ModelPart singleLid;
	private final ModelPart singleBottom;
	private final ModelPart singleLock;
	private final ModelPart rightLid;
	private final ModelPart rightBottom;
	private final ModelPart rightLock;
	private final ModelPart leftLid;
	private final ModelPart leftBottom;
	private final ModelPart leftLock;
	private boolean isChristmas;

	public ChestMimicBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
			this.isChristmas = true;
		}

		ModelPart root = context.bakeLayer(ModelLayers.CHEST);
		this.singleBottom = root.getChild("bottom");
		this.singleLid = root.getChild("lid");
		this.singleLock = root.getChild("lock");
		ModelPart leftRoot = context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
		this.leftBottom = leftRoot.getChild("bottom");
		this.leftLid = leftRoot.getChild("lid");
		this.leftLock = leftRoot.getChild("lock");
		ModelPart rightRoot = context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
		this.rightBottom = rightRoot.getChild("bottom");
		this.rightLid = rightRoot.getChild("lid");
		this.rightLock = rightRoot.getChild("lock");
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Level world = tileEntityIn.getLevel();
		BlockState blockstate = world != null ? tileEntityIn.getBlockState() : AetherBlocks.CHEST_MIMIC.get().defaultBlockState().setValue(ChestMimicBlock.FACING, Direction.SOUTH);
		ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockstate.getBlock();
		if (block instanceof ChestMimicBlock) {
			ChestMimicBlock chestMimicBlock = (ChestMimicBlock) block;
			matrixStackIn.pushPose();
			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
			matrixStackIn.translate(0.5D, 0.5D, 0.5D);
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestMimicBlockEntity> icallbackwrapper;
			if (world != null) {
				icallbackwrapper = chestMimicBlock.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
			} else {
				icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
			}
			combinedLightIn = icallbackwrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
			Material rendermaterial = this.getMaterial(tileEntityIn, chesttype);
			VertexConsumer ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutout);
			if (chesttype != ChestType.SINGLE) {
				if (chesttype == ChestType.LEFT) {
					this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLock, this.leftBottom, combinedLightIn, combinedOverlayIn);
				} else {
					this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLock, this.rightBottom, combinedLightIn, combinedOverlayIn);
				}
			} else {
				this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLock, this.singleBottom, combinedLightIn, combinedOverlayIn);
			}
			matrixStackIn.popPose();
		} else {
			LogManager.getLogger(ChestMimicBlockEntityRenderer.class).warn("Invalid block used with ChestMimicTileEntityRenderer!");
		}
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
