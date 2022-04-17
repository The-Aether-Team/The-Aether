package com.gildedgames.aether.common.world.builders;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.FluidState;

import java.util.List;

public class AetherFeatureBuilders {
    public static AercloudConfiguration createAercloudConfig(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
    }

    // TODO investigate changing this to triangle
    public static List<PlacementModifier> createAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance));
    }

    public static LakeFeature.Configuration lake(BlockStateProvider fluid, BlockStateProvider barrier) {
        return new LakeFeature.Configuration(fluid, barrier);
    }

    public static SpringConfiguration spring(FluidState fluid, boolean requiresBlocksBelow, int rockCount, int holeCount, HolderSet<Block> validBlocks) {
        return new SpringConfiguration(fluid, requiresBlocksBelow, rockCount, holeCount, validBlocks);
    }

    public static RandomPatchConfiguration grassPatch(BlockStateProvider block, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    public static RandomPatchConfiguration tallGrassPatch(BlockStateProvider block) {
        return FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block));
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static BlockPredicateFilter copyBlockSurvivability(Block block) {
        return copyBlockSurvivability(block.defaultBlockState());
    }

    public static BlockPredicateFilter copyBlockSurvivability(BlockState blockState) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(blockState, BlockPos.ZERO));
    }

    public static PlacedFeature treeBlendDensity(int perLayerCount) {
        return new PlacedFeature(Holder.hackyErase(AetherFeatures.ConfiguredFeatures.TREE_BLEND), List.of(CountOnEveryLayerPlacement.of(perLayerCount)));
    }

}
