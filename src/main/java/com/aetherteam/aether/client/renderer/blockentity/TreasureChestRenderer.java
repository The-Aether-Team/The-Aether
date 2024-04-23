package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.client.AetherAtlases;
import io.github.fabricators_of_create.porting_lib.util.MaterialChest;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.ChestType;

public class TreasureChestRenderer extends ChestRenderer<TreasureChestBlockEntity> implements MaterialChest<TreasureChestBlockEntity> {
	public TreasureChestRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public Material getMaterial(TreasureChestBlockEntity blockEntity, ChestType chestType) {
		return switch (chestType) {
			case LEFT -> AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
			case RIGHT -> AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
			case SINGLE -> AetherAtlases.TREASURE_CHEST_MATERIAL;
		};
	}
}
