package com.aetherteam.aether.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * [CODE COPY] - {@link net.minecraft.world.level.block.FrostedIceBlock}.
 */
public interface MeltingBehavior {
    default void tick(Block block, BlockState state, ServerLevel level, BlockPos pos, RandomSource random, IntegerProperty age) {
        if ((random.nextInt(3) == 0 || this.fewerNeigboursThan(block, level, pos, 4)) && this.slightlyMelt(state, level, pos, age)) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (Direction direction : Direction.values()) {
                mutablePos.setWithOffset(pos, direction);
                BlockState blockState = level.getBlockState(mutablePos);
                if (blockState.is(block) && !this.slightlyMelt(blockState, level, mutablePos, age)) {
                    level.scheduleTick(mutablePos, block, Mth.nextInt(random, 20, 40));
                }
            }
        } else {
            level.scheduleTick(pos, block, Mth.nextInt(random, 20, 40));
        }
    }

    default boolean fewerNeigboursThan(Block block, BlockGetter level, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            mutablePos.setWithOffset(pos, direction);
            if (level.getBlockState(mutablePos).is(block)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }
        return true;
    }

    default boolean slightlyMelt(BlockState state, Level level, BlockPos pos, IntegerProperty age) {
        int i = state.getValue(age);
        if (i < 3) {
            level.setBlock(pos, state.setValue(age, i + 1), 2);
            return false;
        } else {
            this.melt(state, level, pos, age);
            return true;
        }
    }

    void melt(BlockState state, Level level, BlockPos pos, IntegerProperty age);
}
