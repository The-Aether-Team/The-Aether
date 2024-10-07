package com.aetherteam.aether.world.placementmodifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import javax.annotation.Nullable;
import java.util.stream.Stream;

/**
 * [CODE COPY] - {@link net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement}.<br><br>
 * Improved to support custom parameters for the heightmap type and the vertical space necessary for the feature to place.
 */
public class ImprovedLayerPlacementModifier extends PlacementModifier {
    public static final MapCodec<ImprovedLayerPlacementModifier> CODEC = RecordCodecBuilder.mapCodec((codec) -> codec.group(
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

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        Stream.Builder<BlockPos> builder = Stream.builder();
        int i = 0;
        boolean flag;
        do {
            flag = false;
            for (int j = 0; j < this.count.sample(random); ++j) {
                int x = random.nextInt(16) + pos.getX();
                int z = random.nextInt(16) + pos.getZ();
                int height = context.getHeight(this.heightmap, x, z);
                BlockPos blockPos = this.findOnGroundPosition(context, new BlockPos(x, height, z), i);
                if (blockPos != null) {
                    builder.add(blockPos);
                    flag = true;
                }
            }
            ++i;
        } while (flag);
        return builder.build();
    }

    @Override
    public PlacementModifierType<?> type() {
        return AetherPlacementModifiers.IMPROVED_LAYER_PLACEMENT.get();
    }

    @Nullable
    private BlockPos findOnGroundPosition(PlacementContext context, BlockPos pos, int count) {
        int i = 0;
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();
        for (int j = y; j >= context.getMinBuildHeight() + 1; --j) {
            BlockPos blockPos = new BlockPos(x, j, z);
            BlockState blockState = context.getBlockState(blockPos);
            BlockState belowState = context.getBlockState(blockPos.below());
            if (blockState.isAir() && this.isSolid(belowState) && !belowState.is(Blocks.BEDROCK) && this.checkVerticalBounds(context, blockPos)) {
                if (i == count) {
                    return blockPos;
                }
                ++i;
            }
        }
        return null;
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
