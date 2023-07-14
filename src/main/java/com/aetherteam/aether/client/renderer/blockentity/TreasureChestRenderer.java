package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.client.AetherAtlases;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

import javax.annotation.Nonnull;

public class TreasureChestRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T>
{
	public TreasureChestRenderer(BlockEntityRendererProvider.Context p_173607_) {
		super(p_173607_);
	}

	@Nonnull
	@Override
	protected Material getMaterial(@Nonnull T blockEntity, @Nonnull ChestType chestType) {
		return switch (chestType) {
			case LEFT -> AetherAtlases.TREASURE_CHEST_LEFT_MATERIAL;
			case RIGHT -> AetherAtlases.TREASURE_CHEST_RIGHT_MATERIAL;
			case SINGLE -> AetherAtlases.TREASURE_CHEST_MATERIAL;
		};
	}
}
