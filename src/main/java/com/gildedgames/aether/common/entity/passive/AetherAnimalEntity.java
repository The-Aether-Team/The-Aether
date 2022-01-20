package com.gildedgames.aether.common.entity.passive;

import java.util.Random;

import com.gildedgames.aether.common.registry.AetherBlocks;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

public abstract class AetherAnimalEntity extends Animal
{
	protected AetherAnimalEntity(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return level.getBlockState(pos.below()).is(AetherBlocks.AETHER_GRASS_BLOCK.get()) ? 10.0F : level.getMaxLocalRawBrightness(pos) - 0.5F;
	}

	public static boolean canAetherAnimalSpawn(EntityType<? extends AetherAnimalEntity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
		return worldIn.getBlockState(pos.below()).is(AetherTags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON) && worldIn.getRawBrightness(pos, 0) > 8;
	}
}
