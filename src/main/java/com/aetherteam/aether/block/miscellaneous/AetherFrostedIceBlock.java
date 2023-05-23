package com.aetherteam.aether.block.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFrostedIceBlock extends FrostedIceBlock {
    public AetherFrostedIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if ((random.nextInt(3) == 0 || this.fewerNeigboursThan(level, pos, 4)) && this.slightlyMelt(state, level, pos)) {
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
            for (Direction direction : Direction.values()) {
                mutablePos.setWithOffset(pos, direction);
                BlockState blockState = level.getBlockState(mutablePos);
                if (blockState.is(this) && !this.slightlyMelt(blockState, level, mutablePos)) {
                    level.scheduleTick(mutablePos, this, Mth.nextInt(random, 20, 40));
                }
            }
        } else {
            level.scheduleTick(pos, this, Mth.nextInt(random, 20, 40));
        }
    }

    private boolean slightlyMelt(BlockState state, Level level, BlockPos pos) {
        int i = state.getValue(AGE);
        if (i < 3) {
            level.setBlock(pos, state.setValue(AGE, i + 1), 2);
            return false;
        } else {
            this.melt(state, level, pos);
            return true;
        }
    }

    private boolean fewerNeigboursThan(BlockGetter level, BlockPos pos, int neighborsRequired) {
        int i = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.values()) {
            mutablePos.setWithOffset(pos, direction);
            if (level.getBlockState(mutablePos).is(this)) {
                ++i;
                if (i >= neighborsRequired) {
                    return false;
                }
            }
        }
        return true;
    }
}
