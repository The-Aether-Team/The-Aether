package com.gildedgames.aether.block.natural;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;

public class PinkAercloudBlock extends AercloudBlock {

	public PinkAercloudBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		
		if (entity.ticksExisted % 20 == 0 && entity instanceof LivingEntity) {
			((LivingEntity) entity).heal(1.0F);
		}
	}
	
}
