package com.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;

public class BouncyAercloudBlock extends TintedAercloudBlock {

	public BouncyAercloudBlock(int hexColor, int updatedHexColor, Block.Properties properties) {
		super(hexColor, updatedHexColor, properties);
		
		shape = VoxelShapes.empty();
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.fallDistance = 0.0F;
		
		Vec3d motion = entity.getMotion();
		
		if (entity.isSneaking()) {
			if (motion.y < 0) {
				entity.setMotion(motion.mul(1.0, 0.005, 1.0));
			}
			return;
		}
		
		entity.setMotion(new Vec3d(motion.x, 2.0, motion.y));
		
		if (world.isRemote) {
			for (int count = 0; count < 50; count++) {
				double xOffset = pos.getX() + world.rand.nextDouble();
				double yOffset = pos.getY() + world.rand.nextDouble();
				double zOffset = pos.getZ() + world.rand.nextDouble();
				
				world.addParticle(ParticleTypes.SPLASH, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
			}
		}
	}
	
}
