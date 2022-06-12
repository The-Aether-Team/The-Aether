package com.gildedgames.aether.common.world.builders;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.AetherLakeConfiguration;
import com.gildedgames.aether.common.world.gen.placement.ConfigFilter;
import com.gildedgames.aether.common.world.gen.placement.RangeFromHeightmapPlacement;
import com.gildedgames.aether.core.AetherConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.FluidState;

import java.util.List;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;

public class AetherFeatureBuilders {
    public static AercloudConfiguration createAercloudConfig(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
    }

    // TODO investigate changing this to triangle
    public static List<PlacementModifier> createAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance));
    }

    public static List<PlacementModifier> createPinkAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(chance),
                new ConfigFilter(AetherConfig.COMMON.generate_pink_aerclouds));
    }

    public static AetherLakeConfiguration lake(BlockStateProvider fluid, BlockStateProvider top) {
        return new AetherLakeConfiguration(fluid, top);
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

    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier p_195485_) { //todo see if rearranging the heightmap thing somewhere else will fix spawning in lakes. also spawning on crystal trees is still not solved.
        return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(TREE_THRESHOLD).add(RangeFromHeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR)).add(BiomeFilter.biome());
        //return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier p_195480_) {
        return treePlacementBase(p_195480_).build();
    }

//    public static PlacedFeature treeBlendDensity(int perLayerCount) {
//        return new PlacedFeature(Holder.hackyErase(AetherFeatures.ConfiguredFeatures.TREE_BLEND), List.of(CountOnEveryLayerPlacement.of(perLayerCount)));
//    }

}
