package com.aether.client.renderer.tileentity;

import com.aether.registry.AetherBlocks;
import com.aether.block.dungeon.ChestMimicBlock;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class ChestMimicTileEntityRenderer<T extends TileEntity> extends TileEntityRenderer<T> {
	private final ModelRenderer singleLid;
	private final ModelRenderer singleBottom;
	private final ModelRenderer singleLatch;
	private boolean isChristmas;

	public ChestMimicTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.isChristmas = true;
		}

		this.singleBottom = new ModelRenderer(64, 64, 0, 19);
		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.singleLid = new ModelRenderer(64, 64, 0, 0);
		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.singleLid.rotationPointY = 9.0F;
		this.singleLid.rotationPointZ = 1.0F;
		this.singleLatch = new ModelRenderer(64, 64, 0, 0);
		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.singleLatch.rotationPointY = 8.0F;
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		World world = tileEntityIn.getWorld();
		BlockState blockstate = (world != null)? tileEntityIn.getBlockState() : AetherBlocks.CHEST_MIMIC.get().getDefaultState().with(ChestMimicBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();
		if (block instanceof ChestMimicBlock) {
			matrixStackIn.push();
			float f = blockstate.get(ChestMimicBlock.FACING).getHorizontalAngle();
			matrixStackIn.translate(0.5, 0.5, 0.5);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
			matrixStackIn.translate(-0.5, -0.5, -0.5);
			RenderMaterial material = this.getMaterial(tileEntityIn);
			IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);
			this.singleLid.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
			this.singleLatch.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
			this.singleBottom.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
			matrixStackIn.pop();
		} else {
			LogManager.getLogger(ChestMimicTileEntityRenderer.class).warn("Invalid block used with ChestMimicTileEntityRenderer!");
		}
	}
	
	protected RenderMaterial getMaterial(T tileEntity) {
		return this.isChristmas? Atlases.CHEST_XMAS_MATERIAL : Atlases.CHEST_MATERIAL;
	}

}
