package com.aetherteam.aether.world.feature;

import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class AercloudFeature extends Feature<AercloudConfiguration> {
    public AercloudFeature(Codec<AercloudConfiguration> codec) {
        super(codec);
    }

    /**
     * Randomly places an area blocks in a direction to create a cloud.
     * The code is taken from older versions.
     *
     * @param context The {@link FeaturePlaceContext} with a {@link AercloudConfiguration}.
     * @return Whether the placement was successful, as a {@link Boolean}.
     */
    @Override
    public boolean place(FeaturePlaceContext<AercloudConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        boolean direction = random.nextBoolean();
        BlockPos blockPos = context.origin().offset(-random.nextInt(8), 0, (direction ? 8 : 0) - random.nextInt(8));
        AercloudConfiguration config = context.config();
        BlockState blockState = config.block().getState(random, blockPos);

        for (int amount = 0; amount < config.bounds(); ++amount) {
            int xOffset = random.nextInt(2);
            int yOffset = (random.nextBoolean() ? random.nextInt(3) - 1 : 0);
            int zOffset = random.nextInt(2);

            if (direction) {
                blockPos = blockPos.offset(xOffset, yOffset, -zOffset);
            } else {
                blockPos = blockPos.offset(xOffset, yOffset, zOffset);
            }

            for (int x = blockPos.getX(); x < blockPos.getX() + random.nextInt(2) + 3; ++x) {
                for (int y = blockPos.getY(); y < blockPos.getY() + random.nextInt(1) + 2; ++y) {
                    for (int z = blockPos.getZ(); z < blockPos.getZ() + random.nextInt(2) + 3; ++z) {
                        BlockPos newPosition = new BlockPos(x, y, z);

                        if (level.isEmptyBlock(newPosition)) {
                            if (Math.abs(x - blockPos.getX()) + Math.abs(y - blockPos.getY()) + Math.abs(z - blockPos.getZ()) < 4 + random.nextInt(2)) {
                                this.setBlock(level, newPosition, blockState);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
