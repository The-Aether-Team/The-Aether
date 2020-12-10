package com.aether.world.gen.feature;

import com.aether.block.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class QuicksoilFeature extends Feature<NoFeatureConfig> {

    public QuicksoilFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        for(int x = pos.getX() - 3; x < pos.getX() + 4; x++) {
            if(reader.getBlockState(pos).isSolidSide(reader, pos, Direction.getRandomDirection(rand))) {
                for(int z = pos.getZ() - 3; z < pos.getZ() + 4; z++) {
                    BlockPos newPos = new BlockPos(x, pos.getY(), z);

                    if(!reader.getBlockState(pos).isAir() && (x - pos.getX()) * (x - pos.getX()) + (z - pos.getZ()) * (z - pos.getZ()) < 12) {
                        reader.setBlockState(newPos, AetherBlocks.QUICKSOIL.getDefaultState(), 0);
                    }
                }
            }

        }

        return false;
    }
}
