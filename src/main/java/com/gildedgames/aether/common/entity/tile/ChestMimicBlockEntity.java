package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChestMimicBlockEntity extends BlockEntity
{
	public ChestMimicBlockEntity(BlockPos pos, BlockState state) {
		super(AetherTileEntityTypes.CHEST_MIMIC.get(), pos, state);
	}

	public ChestMimicBlockEntity() {
		super(AetherTileEntityTypes.CHEST_MIMIC.get(), BlockPos.ZERO, AetherBlocks.CHEST_MIMIC.get().defaultBlockState());
	}
}
