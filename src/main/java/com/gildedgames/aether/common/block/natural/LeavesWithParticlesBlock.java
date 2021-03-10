package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.ParticleStatus;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

public class LeavesWithParticlesBlock extends LeavesBlock implements IAetherDoubleDropBlock
{
	private final Supplier<BasicParticleType> particle;

	public LeavesWithParticlesBlock(Supplier<BasicParticleType> particle, AbstractBlock.Properties properties) {

		super(properties.isValidSpawn((state, reader, pos, entity) -> (entity == EntityType.OCELOT || entity == EntityType.PARROT)).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false));
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
		this.particle = particle;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);
		
		if (worldIn.isClientSide) {
			if (Minecraft.getInstance().options.particles != ParticleStatus.MINIMAL) {
				if (rand.nextInt(10) == 0) {
					for (int i = 0; i < 15; i++) {
						double x = pos.getX() + (rand.nextFloat() - 0.5) * 8.0;
						double y = pos.getY() + (rand.nextFloat() - 0.5) * 8.0;
						double z = pos.getZ() + (rand.nextFloat() - 0.5) * 8.0;
						double dx = (rand.nextFloat() - 0.5) * 0.5;
						double dy = (rand.nextFloat() - 0.5) * 0.5;
						double dz = (rand.nextFloat() - 0.5) * 0.5;

						worldIn.addParticle(this.particle.get(), x, y, z, dx, dy, dz);
					}
				}
			}
		}
	}
}
