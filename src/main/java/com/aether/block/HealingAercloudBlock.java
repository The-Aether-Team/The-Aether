package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HealingAercloudBlock extends AercloudBlock {

	public HealingAercloudBlock(Block.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		
		if (entity.ticksExisted % 20 == 0 && entity instanceof LivingEntity) {
			((LivingEntity) entity).heal(1.0f);
		}
	}
	
}
