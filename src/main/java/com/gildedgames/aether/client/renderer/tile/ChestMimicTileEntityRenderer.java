package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.common.entity.tile.ChestMimicTileEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.block.dungeon.ChestMimicBlock;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import java.util.Calendar;

public class ChestMimicTileEntityRenderer<T extends TileEntity> extends TileEntityRenderer<T>
{
	private final ModelRenderer singleLid;
	private final ModelRenderer singleBottom;
	private final ModelRenderer singleLatch;
	private final ModelRenderer rightLid;
	private final ModelRenderer rightBottom;
	private final ModelRenderer rightLatch;
	private final ModelRenderer leftLid;
	private final ModelRenderer leftBottom;
	private final ModelRenderer leftLatch;
	private boolean isChristmas;

	public ChestMimicTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
			this.isChristmas = true;
		}

		this.singleBottom = new ModelRenderer(64, 64, 0, 19);
		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.singleLid = new ModelRenderer(64, 64, 0, 0);
		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.singleLid.y = 9.0F;
		this.singleLid.z = 1.0F;
		this.singleLatch = new ModelRenderer(64, 64, 0, 0);
		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.singleLatch.y = 8.0F;
		this.rightBottom = new ModelRenderer(64, 64, 0, 19);
		this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.rightLid = new ModelRenderer(64, 64, 0, 0);
		this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.rightLid.y = 9.0F;
		this.rightLid.z = 1.0F;
		this.rightLatch = new ModelRenderer(64, 64, 0, 0);
		this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.rightLatch.y = 8.0F;
		this.leftBottom = new ModelRenderer(64, 64, 0, 19);
		this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.leftLid = new ModelRenderer(64, 64, 0, 0);
		this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.leftLid.y = 9.0F;
		this.leftLid.z = 1.0F;
		this.leftLatch = new ModelRenderer(64, 64, 0, 0);
		this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.leftLatch.y = 8.0F;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		World world = tileEntityIn.getLevel();
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
			TileEntityMerger.ICallbackWrapper<? extends ChestMimicTileEntity> icallbackwrapper;
			if (world != null) {
				icallbackwrapper = chestMimicBlock.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
			} else {
				icallbackwrapper = TileEntityMerger.ICallback::acceptNone;
			}
			combinedLightIn = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
			RenderMaterial rendermaterial = this.getMaterial(tileEntityIn, chesttype);
			IVertexBuilder ivertexbuilder = rendermaterial.buffer(bufferIn, RenderType::entityCutout);
			if (chesttype != ChestType.SINGLE) {
				if (chesttype == ChestType.LEFT) {
					this.renderModels(matrixStackIn, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, combinedLightIn, combinedOverlayIn);
				} else {
					this.renderModels(matrixStackIn, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, combinedLightIn, combinedOverlayIn);
				}
			} else {
				this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, combinedLightIn, combinedOverlayIn);
			}
			matrixStackIn.popPose();
		} else {
			LogManager.getLogger(ChestMimicTileEntityRenderer.class).warn("Invalid block used with ChestMimicTileEntityRenderer!");
		}
	}

	@OnlyIn(Dist.CLIENT)
	private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, int combinedLightIn, int combinedOverlayIn) {
		chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}

	protected RenderMaterial getMaterial(T tileEntity, ChestType chestType) {
		return Atlases.chooseMaterial(tileEntity, chestType, this.isChristmas);
	}
}
