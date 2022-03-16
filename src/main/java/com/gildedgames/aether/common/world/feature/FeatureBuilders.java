package com.gildedgames.aether.common.world.feature;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.worldgen.AetherConfiguredFeatures;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.function.Supplier;

public class FeatureBuilders {
    public static AercloudConfiguration createAercloudConfig(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds,
                BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
    }

    // TODO investigate changing this to triangle
    public static List<PlacementModifier> createAercloudPlacements(int height, int chance) {
        return List.of(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(height)),
                InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(chance));
    }

    public static BlockState getDoubleDrops(Supplier<? extends Block> block) {
        return getDoubleDrops(block.get());
    }

    public static BlockState getDoubleDrops(Block block) {
        return getDoubleDrops(block.defaultBlockState());
    }

    public static BlockState getDoubleDrops(BlockState blockState) {
        return blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
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
        return new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.TREE_BLEND), List.of(CountOnEveryLayerPlacement.of(perLayerCount)));
    }
}
