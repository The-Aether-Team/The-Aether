package com.aether.block;

import java.util.Random;

import com.aether.tileentity.EnchanterTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock;

public class EnchanterBlock extends AbstractAetherFurnaceBlock {

	public EnchanterBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new EnchanterTileEntity();
	}
	
	@Override
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		if (!worldIn.isRemote) { 
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof EnchanterTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (EnchanterTileEntity) tileentity);
			}
		}
	}
	
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.get(LIT)) {
			float x = pos.getX() + 0.5F;
			float y = pos.getY() + 1.0F + (rand.nextFloat() * 6.0F) / 16.0F;
			float z = pos.getZ() + 0.5F;
			
			world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
			
			if (rand.nextDouble() < 0.1) {
				world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
		}
	}	
	
}
