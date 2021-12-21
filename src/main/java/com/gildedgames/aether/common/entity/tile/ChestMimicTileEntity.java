package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChestMimicTileEntity extends BlockEntity
{
	public ChestMimicTileEntity(BlockPos pos, BlockState state) {
		super(AetherTileEntityTypes.CHEST_MIMIC.get(), pos, state);
	}
}
