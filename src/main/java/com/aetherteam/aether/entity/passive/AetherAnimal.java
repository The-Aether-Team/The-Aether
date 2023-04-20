package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.block.AetherBlocks;

import com.aetherteam.aether.AetherTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

public abstract class AetherAnimal extends Animal {
	protected AetherAnimal(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	public static boolean checkAetherAnimalSpawnRules(EntityType<? extends AetherAnimal> animal, LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(AetherTags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
	}
	
	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader level) {
		return level.getBlockState(pos.below()).is(AetherBlocks.AETHER_GRASS_BLOCK.get()) ? 10.0F : level.getMaxLocalRawBrightness(pos) - 0.5F;
	}
}
