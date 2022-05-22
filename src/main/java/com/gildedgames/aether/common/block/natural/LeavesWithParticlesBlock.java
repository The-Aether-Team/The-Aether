package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;
import java.util.function.Supplier;

public class LeavesWithParticlesBlock extends LeavesBlock implements IAetherDoubleDropBlock
{
	private final Supplier<SimpleParticleType> particle;

	public LeavesWithParticlesBlock(Supplier<SimpleParticleType> particle, BlockBehaviour.Properties properties) {

		super(properties.noOcclusion().isValidSpawn((state, reader, pos, entity) -> (entity == EntityType.OCELOT || entity == EntityType.PARROT)).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false));
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
		this.particle = particle;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
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
