package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class AercloudFeature extends Feature<AercloudConfiguration> {
    public AercloudFeature(Codec<AercloudConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<AercloudConfiguration> context) {
        WorldGenLevel reader = context.level();
        Random rand = context.random();
        boolean direction = rand.nextBoolean();
        BlockPos position = context.origin().offset(-rand.nextInt(8), 0, (direction ? 8 : 0) - rand.nextInt(8));
        AercloudConfiguration config = context.config();

        for (int amount = 0; amount < config.bounds(); ++amount) {
            int xOffset = rand.nextInt(2);
            int yOffset = (rand.nextBoolean() ? rand.nextInt(3) - 1 : 0);
            int zOffset = rand.nextInt(2);

            if (direction) {
                position = position.offset(xOffset, yOffset, -zOffset);
            } else {
                position = position.offset(xOffset, yOffset, zOffset);
            }

            for (int x = position.getX(); x < position.getX() + rand.nextInt(2) + 3; ++x) {
                for (int y = position.getY(); y < position.getY() + rand.nextInt(1) + 2; ++y) {
                    for (int z = position.getZ(); z < position.getZ() + rand.nextInt(2) + 3; ++z) {
                        BlockPos newPosition = new BlockPos(x, y, z);

                        if (reader.isEmptyBlock(newPosition)) {
                            if (Math.abs(x - position.getX()) + Math.abs(y - position.getY()) + Math.abs(z - position.getZ()) < 4 + rand.nextInt(2)) {
                                this.setBlock(reader, newPosition, config.block().getState(rand, position));
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
