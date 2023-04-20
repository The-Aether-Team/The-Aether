package com.aetherteam.aether.data.resources.builders;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.aetherteam.aether.world.configuration.AetherLakeConfiguration;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.FluidState;

public class AetherConfiguredFeatureBuilders {
    public static AercloudConfiguration aercloud(int bounds, BlockState blockState) {
        return new AercloudConfiguration(bounds, BlockStateProvider.simple(blockState.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)));
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
}
