package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.ticks.ScheduledTick;

public class IcestoneWallBlock extends WallBlock implements IIcestoneBlock
{
    public IcestoneWallBlock(Properties properties) {
        super(properties);
    }

    //TODO: Test if the switch to ScheduledTick works.
    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        ScheduledTick<Block> scheduledTick = new ScheduledTick<>(this, pos, 10, 0);
        worldIn.getBlockTicks().schedule(scheduledTick);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        freezeFluids(worldIn, pos);
        ScheduledTick<Block> scheduledTick = new ScheduledTick<>(this, pos, 10, 0);
        worldIn.getBlockTicks().schedule(scheduledTick);
    }
}
