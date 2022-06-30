package com.gildedgames.aether.world.generation.tree;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class LevelTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource randomSource, boolean largeHive) {
        return null;
    }

    @Nullable
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(ServerLevel serverLevel, RandomSource randomSource, boolean largeHive);

    public boolean growTree(@Nonnull ServerLevel serverLevel, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull RandomSource randomSource) {
        Holder<? extends ConfiguredFeature<?, ?>> holder = this.getConfiguredFeature(serverLevel, randomSource, this.hasFlowers(serverLevel, pos));
        if (holder == null) {
            return false;
        } else {
            ConfiguredFeature<?, ?> configuredFeature = holder.value();
            BlockState blockState = serverLevel.getFluidState(pos).createLegacyBlock();
            serverLevel.setBlock(pos, blockState, 4);
            if (configuredFeature.place(serverLevel, generator, randomSource, pos)) {
                if (serverLevel.getBlockState(pos) == blockState) {
                    serverLevel.sendBlockUpdated(pos, state, blockState, 2);
                }
                return true;
            } else {
                serverLevel.setBlock(pos, state, 4);
                return false;
            }
        }
    }

    private boolean hasFlowers(LevelAccessor level, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.MutableBlockPos.betweenClosed(pos.below().north(2).west(2), pos.above().south(2).east(2))) {
            if (level.getBlockState(blockpos).is(BlockTags.FLOWERS)) {
                return true;
            }
        }
        return false;
    }
}
