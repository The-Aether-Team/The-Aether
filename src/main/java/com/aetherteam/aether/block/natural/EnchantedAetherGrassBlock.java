package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.mixin.mixins.common.accessor.SpreadingSnowyDirtBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EnchantedAetherGrassBlock extends GrassBlock {
	public EnchantedAetherGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config) {
		placeFunction.accept(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		return true;
	}

	/**
	 * Based on part of {@link net.minecraft.world.level.block.SpreadingSnowyDirtBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.<br><br>
	 * Warning for "deprecation" is suppressed due to being copied from what Forge does.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!SpreadingSnowyDirtBlockAccessor.callCanBeGrass(state, level, pos)) {
			if (!level.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			level.setBlockAndUpdate(pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState());
		}
	}


	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos abovePos = pos.above();
		Block grass = AetherBlocks.AETHER_GRASS_BLOCK.get();
		Optional<Holder.Reference<PlacedFeature>> grassFeatureOptional = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(AetherPlacedFeatures.ENCHANTED_AETHER_GRASS_BONEMEAL);

		start:
		for (int i = 0; i < 128; ++i) {
			BlockPos blockPos = abovePos;

			for (int j = 0; j < i / 16; ++j) {
				blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
				if (!level.getBlockState(blockPos.below()).is(this) || level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
					continue start;
				}
			}

			BlockState blockState = level.getBlockState(blockPos);
			if (blockState.is(grass) && random.nextInt(10) == 0) {
				((BonemealableBlock	) grass).performBonemeal(level, random, blockPos, blockState);
			}

			if (blockState.isAir()) {
				Holder<PlacedFeature> featureHolder;
				if (random.nextInt(8) == 0) {
					List<ConfiguredFeature<?, ?>> list = level.getBiome(blockPos).value().getGenerationSettings().getFlowerFeatures();
					if (list.isEmpty()) {
						continue;
					}
					featureHolder = ((RandomPatchConfiguration) list.get(random.nextInt(list.size())).config()).feature();
				} else {
					if (grassFeatureOptional.isEmpty()) {
						continue;
					}
					featureHolder = grassFeatureOptional.get();
				}
				featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockPos);
			}
		}
	}
}
