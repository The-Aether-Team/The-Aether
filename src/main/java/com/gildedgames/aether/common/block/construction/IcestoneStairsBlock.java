package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class IcestoneStairsBlock extends StairBlock implements IIcestoneBlock
{
    public IcestoneStairsBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        worldIn.getBlockTicks().scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        freezeFluids(worldIn, pos);
        worldIn.getBlockTicks().scheduleTick(pos, this, 10);
    }
}
