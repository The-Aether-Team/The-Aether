package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class QuicksoilFeature extends Feature<NoneFeatureConfiguration>
{
    public QuicksoilFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();
        boolean doesProtrude = (
                (reader.getBlockState(pos.west(3)).isAir() ||
                        reader.getBlockState(pos.north(3)).isAir() ||
                        reader.getBlockState(pos.south(3)).isAir() ||
                        reader.getBlockState(pos.east(3)).isAir()) &&
                (reader.getBlockState(pos).is(AetherTags.Blocks.HOLYSTONE) ||
                        reader.getBlockState(pos).getBlock() == AetherBlocks.AETHER_DIRT.get()));
        if (doesProtrude) {
            for(int x = pos.getX() - 4; x < pos.getX() + 5; x++) {
                for(int z = pos.getZ() - 4; z < pos.getZ() + 5; z++) {
                    BlockPos newPos = new BlockPos(x, pos.getY(), z);

                    if((x - pos.getX()) * (x - pos.getX()) + (z - pos.getZ()) * (z - pos.getZ()) < 12) {
                        reader.setBlock(newPos, AetherBlocks.QUICKSOIL.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 0);
                    }
                }

            }
        }

        return true;
    }
}
