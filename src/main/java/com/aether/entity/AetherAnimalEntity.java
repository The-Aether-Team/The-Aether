package com.aether.entity;

import java.util.Random;

import com.aether.block.AetherBlocks;
import com.aether.item.AetherItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AetherAnimalEntity extends AnimalEntity {

	protected AetherAnimalEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
		return worldIn.getBlockState(pos.down()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK? 10.0F : worldIn.getLight(pos) - 0.5F;
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == AetherItems.BLUEBERRY;
	}

	//Copied from AnimalEntity, changed to check for Aether grass.
	public static boolean canAetherAnimalSpawn(EntityType<? extends AetherAnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.down()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK && worldIn.getLightSubtracted(pos, 0) > 8;
	}

}
