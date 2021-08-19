package com.gildedgames.aether.common.entity.passive;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AetherAnimalEntity extends AnimalEntity
{
	protected AetherAnimalEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, IWorldReader worldIn) {
		return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() ? 10.0F : worldIn.getMaxLocalRawBrightness(pos) - 0.5F;
	}
	
	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == AetherItems.BLUE_BERRY.get();
	}

	public static boolean canAetherAnimalSpawn(EntityType<? extends AetherAnimalEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() && worldIn.getRawBrightness(pos, 0) > 8;
	}
}
