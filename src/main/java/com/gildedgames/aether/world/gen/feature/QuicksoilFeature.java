package com.gildedgames.aether.world.gen.feature;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.registry.AetherBlocks;
import com.gildedgames.aether.registry.AetherTags;
import com.mojang.serialization.Codec;
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
        boolean doesProtrude = (
                (reader.getBlockState(pos.west(3)).isAir() ||
                        reader.getBlockState(pos.north(3)).isAir() ||
                        reader.getBlockState(pos.south(3)).isAir() ||
                        reader.getBlockState(pos.east(3)).isAir()) &&
                (reader.getBlockState(pos).isIn(AetherTags.Blocks.HOLYSTONE) ||
                        reader.getBlockState(pos).getBlock() == AetherBlocks.AETHER_DIRT.get()));
        if (doesProtrude) {
            for(int x = pos.getX() - 3; x < pos.getX() + 4; x++) {
                for(int z = pos.getZ() - 3; z < pos.getZ() + 4; z++) {
                    BlockPos newPos = new BlockPos(x, pos.getY(), z);

                    if((x - pos.getX()) * (x - pos.getX()) + (z - pos.getZ()) * (z - pos.getZ()) < 12) {
                        reader.setBlockState(newPos, AetherBlocks.QUICKSOIL.get().getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, true), 0);
                    }
                }

            }
        }
        return false;
    }
}
