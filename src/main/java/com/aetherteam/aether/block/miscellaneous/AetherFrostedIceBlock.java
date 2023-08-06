package com.aetherteam.aether.block.miscellaneous;

import com.aetherteam.aether.block.MeltingBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFrostedIceBlock extends FrostedIceBlock implements MeltingBehavior {
    public AetherFrostedIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        MeltingBehavior.super.tick(this, state, level, pos, random, AGE);
    }

    @Override
    public void melt(BlockState state, Level level, BlockPos pos) {
        super.melt(state, level, pos);
    }
}
