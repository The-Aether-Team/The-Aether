package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class HolystoneSphereFeature extends Feature<NoneFeatureConfiguration>
{
    public HolystoneSphereFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos, NoneFeatureConfiguration config) {
        int radius = 4;

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    float formula = (float) (Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2) + Math.pow(z - pos.getZ(), 2));

                    if (formula <= Math.pow(radius, 2)) {
                        reader.setBlock(new BlockPos(x, y, z), AetherBlocks.HOLYSTONE.get().defaultBlockState(), 2 | 16);
                    }
                }
            }
        }

        return true;
    }
}
