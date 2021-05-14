package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.block.util.IIcestoneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class IcestoneSlabBlock extends SlabBlock
{
    public IcestoneSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
        worldIn.getBlockTicks().scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        IIcestoneBlock.freezeFluids(worldIn, pos);
        worldIn.getBlockTicks().scheduleTick(pos, this, 10);
    }
}
