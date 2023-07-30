package com.aetherteam.aether.world.feature;

import com.aetherteam.aether.world.BlockPlacementUtil;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShelfFeature extends Feature<ShelfConfiguration> {
    public ShelfFeature(Codec<ShelfConfiguration> codec) {
        super(codec);
    }

    /**
     * Places disks within a y-range and in a 16x16 area, creating continuous shelves along island edges.
     * @param context The {@link FeaturePlaceContext} with a {@link ShelfConfiguration}.
     * @return Whether the placement was successful, as a {@link Boolean}.
     */
    @Override
    public boolean place(FeaturePlaceContext<ShelfConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        ShelfConfiguration config = context.config();

        for (int x = pos.getX(); x < pos.getX() + 16; ++x) {
            for (int z = pos.getZ(); z < pos.getZ() + 16; ++z) {
                for (int y = config.yRange().getMinValue(); y < config.yRange().getMaxValue(); ++y) {
                    BlockPos placementPos = new BlockPos(x, y, z);
                    if (level.getBlockState(placementPos).isAir() && level.getBlockState(placementPos.above()).is(config.validBlocks()) && level.getBlockState(placementPos.above(2)).isAir()) {
                        BlockPlacementUtil.placeDisk(level, config.block(), placementPos, config.radius().sample(random), random);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
