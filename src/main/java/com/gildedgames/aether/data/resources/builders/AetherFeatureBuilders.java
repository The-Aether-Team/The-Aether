package com.gildedgames.aether.data.resources.builders;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.world.configuration.AercloudConfiguration;
import com.gildedgames.aether.world.configuration.AetherLakeConfiguration;
import com.gildedgames.aether.world.placementmodifier.AetherPlacementModifiers;
import com.gildedgames.aether.world.placementmodifier.ConfigFilter;
import com.gildedgames.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.gildedgames.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.gildedgames.aether.AetherConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
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
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance), new DungeonBlacklistFilter());
    }

    public static List<PlacementModifier> createPinkAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(chance),
                new DungeonBlacklistFilter(),
                new ConfigFilter(AetherConfig.COMMON.generate_pink_aerclouds));
    }

    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier p_195485_) {
        return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(TREE_THRESHOLD).add(ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4)).add(BiomeFilter.biome()).add(new DungeonBlacklistFilter());
    }

    public static List<PlacementModifier> treePlacement(PlacementModifier p_195480_) {
        return treePlacementBase(p_195480_).build();
    }

    public static RandomPatchConfiguration grassPatch(BlockStateProvider block, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block)));
    }

    public static RandomPatchConfiguration tallGrassPatch(BlockStateProvider block) {
        return FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(block));
    }

    public static AetherLakeConfiguration lake(BlockStateProvider fluid, BlockStateProvider top) {
        return new AetherLakeConfiguration(fluid, top);
    }

    public static SpringConfiguration spring(FluidState fluid, boolean requiresBlocksBelow, int rockCount, int holeCount, HolderSet<Block> validBlocks) {
        return new SpringConfiguration(fluid, requiresBlocksBelow, rockCount, holeCount, validBlocks);
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }
}
