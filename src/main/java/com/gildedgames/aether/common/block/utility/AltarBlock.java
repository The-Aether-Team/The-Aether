package com.gildedgames.aether.common.block.utility;

import java.util.Random;

import com.gildedgames.aether.common.entity.tile.AltarTileEntity;

import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends AbstractFurnaceBlock
{
	public AltarBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AltarTileEntity(blockPos, blockState);
	}

	@Override
	protected void openContainer(Level worldIn, BlockPos pos, Player player) {
		if (!worldIn.isClientSide) { 
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof AltarTileEntity) {
				player.openMenu((MenuProvider) tileentity);
			}
		}
	}
	
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY() + 1.0 + (rand.nextFloat() * 6.0) / 16.0;
			double z = pos.getZ() + 0.5;
			
			world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
			
			if (rand.nextDouble() < 0.1) {
				world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
			}
		}
	}
}
