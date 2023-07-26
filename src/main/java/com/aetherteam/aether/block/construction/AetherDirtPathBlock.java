package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AetherDirtPathBlock extends DirtPathBlock {
    public AetherDirtPathBlock(Properties properties) {
        super(properties);
    }

    /**
     * [CODE COPY] - {@link DirtPathBlock#getStateForPlacement(BlockPlaceContext)}.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), AetherBlocks.AETHER_DIRT.get().defaultBlockState(), context.getLevel(), context.getClickedPos()) : this.defaultBlockState();
    }

    /**
     * [CODE COPY] - {@link DirtPathBlock#tick(BlockState, ServerLevel, BlockPos, RandomSource)}.
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        AetherFarmBlock.turnToDirt(state, level, pos);
    }
}
