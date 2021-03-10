package com.gildedgames.aether.common.block.utility;

import java.util.Random;

import com.gildedgames.aether.common.entity.tile.AltarTileEntity;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;

public class AltarBlock extends AbstractFurnaceBlock
{
	public AltarBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new AltarTileEntity();
	}
	
	@Override
	protected void openContainer(World worldIn, BlockPos pos, PlayerEntity player) {
		if (!worldIn.isClientSide) { 
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof AltarTileEntity) {
				player.openMenu((INamedContainerProvider) tileentity);
			}
		}
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY() + 1.0 + (rand.nextFloat() * 6.0) / 16.0;
			double z = pos.getZ() + 0.5;
			
			world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
			
			if (rand.nextDouble() < 0.1) {
				world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
		}
	}
}
