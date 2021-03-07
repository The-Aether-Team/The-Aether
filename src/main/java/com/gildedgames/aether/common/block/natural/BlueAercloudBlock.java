package com.gildedgames.aether.common.block.natural;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlueAercloudBlock extends AercloudBlock
{
	protected static VoxelShape SHAPE = VoxelShapes.empty();

	public BlueAercloudBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.fallDistance = 0.0F;
		Vector3d motion = entity.getMotion();

		if (entity.isSneaking()) {
			if (motion.y < 0) {
				entity.setMotion(motion.mul(1.0, 0.005, 1.0));
			}
			return;
		}
		
		entity.setMotion(motion.x, 2.0, motion.z);
		
		if (world.isRemote) {
			for (int count = 0; count < 50; count++) {
				double xOffset = pos.getX() + world.rand.nextDouble();
				double yOffset = pos.getY() + world.rand.nextDouble();
				double zOffset = pos.getZ() + world.rand.nextDouble();
				
				world.addParticle(ParticleTypes.SPLASH, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
