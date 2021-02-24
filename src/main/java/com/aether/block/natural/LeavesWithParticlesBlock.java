package com.aether.block.natural;

import java.util.Random;

import com.aether.block.state.properties.AetherBlockStateProperties;
import com.aether.block.util.IAetherDoubleDropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock;

public class LeavesWithParticlesBlock extends LeavesBlock implements IAetherDoubleDropBlock {
	private final IParticleData particle;
	public static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;
	
	public LeavesWithParticlesBlock(float particleRed, float particleGreen, float particleBlue, AbstractBlock.Properties properties) {
		this(particleRed, particleGreen, particleBlue, 1.0F, properties);
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}
	
	public LeavesWithParticlesBlock(float particleRed, float particleGreen, float particleBlue, float particleAlpha, AbstractBlock.Properties properties) {
		this(new RedstoneParticleData(particleRed, particleGreen, particleBlue, particleAlpha), properties);
	}
	
	public LeavesWithParticlesBlock(IParticleData particle, AbstractBlock.Properties properties) {
		super(properties);
		this.particle = particle;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
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
