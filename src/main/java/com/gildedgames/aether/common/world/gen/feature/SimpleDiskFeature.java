package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.core.util.BlockLogic;
import com.gildedgames.aether.core.util.BlockPlacers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SimpleDiskFeature extends Feature<SimpleDiskConfiguration> {
    public SimpleDiskFeature(Codec<SimpleDiskConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleDiskConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();

        boolean doesProtrude = BlockLogic.doesAirExistNearby(pos, context.config().clearanceRadius(), reader) &&
                (reader.getBlockState(pos).is(AetherTags.Blocks.HOLYSTONE) ||
                        reader.getBlockState(pos).getBlock() == AetherBlocks.AETHER_DIRT.get());

        if (doesProtrude)
            BlockPlacers.placeDisk(pos, context.config().radius(), reader, context.config().block(), context.random());

        return doesProtrude;
    }
}
