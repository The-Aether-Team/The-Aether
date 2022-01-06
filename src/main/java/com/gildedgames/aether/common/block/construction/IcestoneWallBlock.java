package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.block.util.FreezingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class IcestoneWallBlock extends WallBlock implements FreezingBlock
{
    public IcestoneWallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        worldIn.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        this.freezeBlocks(worldIn, pos, state, FreezingBlock.SQRT_8);
        worldIn.scheduleTick(pos, this, 10);
    }
}
