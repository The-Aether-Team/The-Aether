package com.gildedgames.aether.block.entity;

import com.gildedgames.aether.block.AetherBlockEntityTypes;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChestMimicBlockEntity extends BlockEntity
{
	public ChestMimicBlockEntity(BlockPos pos, BlockState state) {
		super(AetherBlockEntityTypes.CHEST_MIMIC.get(), pos, state);
	}

	public ChestMimicBlockEntity() {
		super(AetherBlockEntityTypes.CHEST_MIMIC.get(), BlockPos.ZERO, AetherBlocks.CHEST_MIMIC.get().defaultBlockState());
	}
}
