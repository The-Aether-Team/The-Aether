package com.aether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock;

public class LeavesWithParticlesBlock extends LeavesBlock {
	private final IParticleData particle;
	
	public LeavesWithParticlesBlock(float particleRed, float particleGreen, float particleBlue, AbstractBlock.Properties properties) {
		this(particleRed, particleGreen, particleBlue, 1.0F, properties);
	}
	
	public LeavesWithParticlesBlock(float particleRed, float particleGreen, float particleBlue, float particleAlpha, AbstractBlock.Properties properties) {
		this(new RedstoneParticleData(particleRed, particleGreen, particleBlue, particleAlpha), properties);
	}
	
	public LeavesWithParticlesBlock(IParticleData particle, AbstractBlock.Properties properties) {
		super(properties);
		this.particle = particle;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);
		
		if (!worldIn.isRemote) {
			return;
		}
		
		if (Minecraft.getInstance().gameSettings.particles != ParticleStatus.MINIMAL) {
			if (rand.nextInt(10) == 0) {
				for (int i = 0; i < 15; i++) {
					double x = pos.getX() + (rand.nextFloat() - 0.5) * 8.0;
					double y = pos.getY() + (rand.nextFloat() - 0.5) * 8.0;
					double z = pos.getZ() + (rand.nextFloat() - 0.5) * 8.0;
					double dx = (rand.nextFloat() - 0.5) * 0.5;
					double dy = (rand.nextFloat() - 0.5) * 0.5;
					double dz = (rand.nextFloat() - 0.5) * 0.5;
										
					Minecraft.getInstance().worldRenderer.addParticle(this.particle, false, x, y, z, dx, dy, dz);
				}
			}
		}		
	}
	
}
