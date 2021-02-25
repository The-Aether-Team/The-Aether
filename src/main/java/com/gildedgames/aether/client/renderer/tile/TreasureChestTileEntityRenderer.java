package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.client.renderer.AetherAtlases;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TreasureChestTileEntityRenderer<T extends TileEntity & IChestLid> extends ChestTileEntityRenderer<T> {

	public TreasureChestTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	protected RenderMaterial getMaterial(T tileEntity, ChestType chestType) {
		switch (chestType) {
		case LEFT:
			return AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
		case RIGHT:
			return AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
		case SINGLE:
		default:
			return AetherAtlases.TREASURE_CHEST_MATERIAL;
		}
	}
	
}
