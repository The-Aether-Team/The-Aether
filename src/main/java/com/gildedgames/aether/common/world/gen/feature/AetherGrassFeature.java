package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.Random;

public class AetherGrassFeature extends Feature<RandomPatchConfiguration>
{
    public AetherGrassFeature(Codec<RandomPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel reader = context.level();
        Random random = context.random();
        RandomPatchConfiguration config = context.config();
        if (AetherConfig.COMMON.generate_tall_grass.get()) {

            int i = 0;
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            int xzSpread = config.xzSpread() + 1;
            int ySpread = config.ySpread() + 1;

            for (int j = 0; j < config.tries(); ++j) {
                blockpos$mutable.setWithOffset(blockpos, random.nextInt(xzSpread) - random.nextInt(xzSpread), random.nextInt(ySpread) - random.nextInt(ySpread), random.nextInt(xzSpread) - random.nextInt(xzSpread));
                if (config.feature().get().place(reader, context.chunkGenerator(), random, blockpos$mutable)) {
                    ++i;
                }
            }

            return i > 0;
        }
        
        return true;
    }
}
