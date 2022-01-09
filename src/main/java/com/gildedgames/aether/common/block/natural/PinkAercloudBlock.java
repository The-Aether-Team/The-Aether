package com.gildedgames.aether.common.block.natural;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class PinkAercloudBlock extends AercloudBlock
{
	public PinkAercloudBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		super.entityInside(state, world, pos, entity);
		
		if (entity.tickCount % 20 == 0 && entity instanceof LivingEntity) {
			((LivingEntity) entity).heal(1.0F);
		}
	}
}
