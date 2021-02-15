package com.aether.world.gen.feature;

import com.aether.registry.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class PinkAercloudFeature extends Feature<NoFeatureConfig> {

    public PinkAercloudFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if(true/*TODO: Pink Clouds Config Option*/) {
            BlockPos origin = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            BlockPos position = new BlockPos(origin.getX() + 8, origin.getY(), origin.getZ() + 8);

            for (int amount = 0; amount < 1; ++amount) {
                int xOffset = rand.nextInt(2);
                int yOffset = (rand.nextBoolean() ? rand.nextInt(3) - 1 : 0);
                int zOffset = rand.nextInt(2);

                position = position.add(xOffset, yOffset, zOffset);

                for (int x = position.getX(); x < position.getX() + rand.nextInt(2) + 3; ++x) {
                    for (int y = position.getY(); y < position.getY() + rand.nextInt(1) + 2; ++y) {
                        for (int z = position.getZ(); z < position.getZ() + rand.nextInt(2) + 3; ++z) {
                            BlockPos newPosition = new BlockPos(x, y, z);

                            if (reader.isAirBlock(newPosition)) {
                                if (Math.abs(x - position.getX()) + Math.abs(y - position.getY()) + Math.abs(z - position.getZ()) < 4 + rand.nextInt(2)) {
                                    this.setBlockState(reader, newPosition, AetherBlocks.PINK_AERCLOUD.get().getDefaultState());
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
