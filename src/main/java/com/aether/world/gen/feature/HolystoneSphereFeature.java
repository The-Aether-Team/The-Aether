package com.aether.world.gen.feature;

import com.aether.block.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class HolystoneSphereFeature extends Feature<NoFeatureConfig> {

    public HolystoneSphereFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int radius = 4;

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    float formula = (float) (Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2) + Math.pow(z - pos.getZ(), 2));

                    if (formula <= Math.pow(radius, 2)) {
                        reader.setBlockState(new BlockPos(x, y, z), AetherBlocks.HOLYSTONE.getDefaultState(), 2 | 16);
                    }
                }
            }
        }
        return true;
    }
}
