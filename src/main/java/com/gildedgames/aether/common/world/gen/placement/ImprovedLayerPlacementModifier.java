package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.Stream;

public class ImprovedLayerPlacementModifier extends PlacementModifier {
    public static final Codec<ImprovedLayerPlacementModifier> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
            Heightmap.Types.CODEC.fieldOf("heightmap").forGetter((modifier) -> modifier.heightmap),
            IntProvider.codec(0, 256).fieldOf("count").forGetter((modifier) -> modifier.count),
            Codec.INT.optionalFieldOf("verticalBounds", Integer.MIN_VALUE).forGetter((modifier) -> modifier.verticalBounds)
    ).apply(codec, ImprovedLayerPlacementModifier::new));
    private final Heightmap.Types heightmap;
    private final IntProvider count;
    private final int verticalBounds;

    private ImprovedLayerPlacementModifier(Heightmap.Types heightmap, IntProvider count, int verticalBounds) {
        this.heightmap = heightmap;
        this.count = count;
        this.verticalBounds = verticalBounds;
    }

    public static ImprovedLayerPlacementModifier of(Heightmap.Types heightmap, IntProvider count, int verticalBounds) {
        return new ImprovedLayerPlacementModifier(heightmap, count, verticalBounds);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(@Nonnull PlacementContext context, @Nonnull Random random, @Nonnull BlockPos pos) {
        Stream.Builder<BlockPos> builder = Stream.builder();
        int i = 0;
        boolean flag;
        do {
            flag = false;
            for (int j = 0; j < this.count.sample(random); ++j) {
                int x = pos.getX();
                int z = pos.getZ();
                int height = context.getHeight(this.heightmap, x, z);
                if (j > 0) {
                    x += random.nextInt(4) + 1;
                    z += random.nextInt(4) + 1;
                }
                int y = this.findOnGroundYPosition(context, x, height, z, i);
                if (y != Integer.MAX_VALUE) {
                    builder.add(new BlockPos(x, y, z));
                    flag = true;
                }
            }
            ++i;
        } while (flag);
        return builder.build();
    }

    @Nonnull
    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifiers.IMPROVED_LAYER_PLACEMENT;
    }

    private int findOnGroundYPosition(PlacementContext context, int x, int y, int z, int count) {
        int i = 0;
        for (int j = y; j >= context.getMinBuildHeight() + 1; --j) {
            BlockPos blockPos = new BlockPos(x, j, z);
            BlockState blockState = context.getBlockState(blockPos);
            BlockState blockState1 = context.getBlockState(blockPos.below());
            if (blockState.isAir() && this.isSolid(blockState1) && !blockState1.is(Blocks.BEDROCK) && checkVerticalBounds(context, blockPos)) {
                if (i == count) {
                    return blockPos.getY();
                }
                ++i;
            }
        }
        return Integer.MAX_VALUE;
    }

    private boolean checkVerticalBounds(PlacementContext context, BlockPos pos) {
        int i = pos.getX();
        int j = pos.getZ();
        int k = pos.getY();
        boolean flag = true;
        for (int y = k; y < k + this.verticalBounds; y++) {
            BlockPos blockPos = new BlockPos(i, y, j);
            if (this.isSolid(context.getBlockState(blockPos))) {
                flag = false;
            }
        }
        return flag;
    }

    private boolean isSolid(BlockState state) {
        return !state.isAir() && !state.is(Blocks.WATER) && !state.is(Blocks.LAVA);
    }
}
