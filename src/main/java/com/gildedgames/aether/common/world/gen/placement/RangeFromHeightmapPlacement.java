package com.gildedgames.aether.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.Stream;

public class RangeFromHeightmapPlacement extends PlacementModifier {
    public static final Codec<RangeFromHeightmapPlacement> CODEC = RecordCodecBuilder.create((p_191701_) -> p_191701_.group(
            Heightmap.Types.CODEC.fieldOf("heightmap").forGetter((p_191705_) -> p_191705_.heightmap)).apply(p_191701_, RangeFromHeightmapPlacement::new));
    private final Heightmap.Types heightmap;

    private RangeFromHeightmapPlacement(Heightmap.Types heightmap) {
        this.heightmap = heightmap;
    }

    public static RangeFromHeightmapPlacement onHeightmap(Heightmap.Types heightmap) {
        return new RangeFromHeightmapPlacement(heightmap);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, @Nonnull Random random, BlockPos pos) {
        Stream.Builder<BlockPos> builder = Stream.builder();
        int i = pos.getX(); //todo random offset.
        int j = pos.getZ();
        int k = context.getHeight(this.heightmap, i, j);
        for (int y = k; y > context.getMinBuildHeight(); y--) {
            BlockPos blockPos = new BlockPos(i, y, j);
            if (context.getBlockState(blockPos).isAir() && !this.isEmpty(context.getBlockState(blockPos.below()))) {
                builder.add(blockPos);
            }
        }
        return builder.build();
    }

    private boolean isEmpty(BlockState p_191609_) {
        return p_191609_.isAir() || p_191609_.is(Blocks.WATER) || p_191609_.is(Blocks.LAVA);
    }

    @Nonnull
    @Override
    public PlacementModifierType<?> type() {
        return PlacementModifiers.RANGE_FROM_HEIGHTMAP;
    }
}
