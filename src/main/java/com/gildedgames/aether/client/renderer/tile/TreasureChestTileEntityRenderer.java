package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.client.registry.AetherAtlases;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TreasureChestTileEntityRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T> {

	public TreasureChestTileEntityRenderer(BlockEntityRendererProvider.Context p_173607_) {
		super(p_173607_);
	}


	@Override
	protected Material getMaterial(T tileEntity, ChestType chestType) {
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
