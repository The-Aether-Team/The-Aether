package com.aether.client.renderer.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aether.tileentity.ChestMimicTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestMimicItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
	private static final Logger LOGGER = LogManager.getLogger();
	private /*final*/ ChestMimicTileEntity chestMimic;// = new ChestMimicTileEntity();
	
	@Override
	public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ChestMimicTileEntity chestMimic = this.chestMimic;
		if (chestMimic == null) {
			this.chestMimic = chestMimic = new ChestMimicTileEntity();
		}
		TileEntityRendererDispatcher.instance.renderItem(chestMimic, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
	
}
