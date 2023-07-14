package com.aetherteam.aether.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PinkAercloudBlock extends AercloudBlock {
	public PinkAercloudBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Heals the player for a half heart every 20 ticks, on top of the normal Aercloud behavior in {@link AercloudBlock#entityInside(BlockState, Level, BlockPos, Entity)}.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param entity The {@link Entity} in the block.
	 */
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);
		if (entity instanceof LivingEntity livingEntity && livingEntity.tickCount % 20 == 0) {
			livingEntity.heal(1.0F);
		}
	}
}
