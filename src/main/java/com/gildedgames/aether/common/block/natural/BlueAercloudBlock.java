package com.gildedgames.aether.common.block.natural;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class BlueAercloudBlock extends AercloudBlock
{
	protected static VoxelShape SHAPE = Shapes.empty();

	public BlueAercloudBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.fallDistance *= 0.0F;
		Vec3 motion = entity.getDeltaMovement();

		if (entity.isShiftKeyDown()) {
			if (motion.y < 0) {
				entity.setDeltaMovement(motion.multiply(1.0, 0.005, 1.0));
			}
			return;
		}
		
		entity.setDeltaMovement(motion.x, 2.0, motion.z);
		
		if (world.isClientSide) {
			for (int count = 0; count < 50; count++) {
				double xOffset = pos.getX() + world.random.nextDouble();
				double yOffset = pos.getY() + world.random.nextDouble();
				double zOffset = pos.getZ() + world.random.nextDouble();
				
				world.addParticle(ParticleTypes.SPLASH, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
