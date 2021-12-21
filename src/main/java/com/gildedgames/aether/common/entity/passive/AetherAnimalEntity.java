package com.gildedgames.aether.common.entity.passive;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

public abstract class AetherAnimalEntity extends Animal
{
	protected AetherAnimalEntity(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
		return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() ? 10.0F : worldIn.getMaxLocalRawBrightness(pos) - 0.5F;
	}
	
	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == AetherItems.BLUE_BERRY.get();
	}

	public static boolean canAetherAnimalSpawn(EntityType<? extends AetherAnimalEntity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() && worldIn.getRawBrightness(pos, 0) > 8;
	}
}
