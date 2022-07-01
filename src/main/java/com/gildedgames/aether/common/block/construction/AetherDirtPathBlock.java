package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AetherDirtPathBlock extends DirtPathBlock {
    public AetherDirtPathBlock(BlockBehaviour.Properties p_153129_) {
        super(p_153129_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_153131_) {
        return !this.defaultBlockState().canSurvive(p_153131_.getLevel(), p_153131_.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), AetherBlocks.AETHER_DIRT.get().defaultBlockState(), p_153131_.getLevel(), p_153131_.getClickedPos()) : this.defaultBlockState();
    }

    @Override
    public void tick(BlockState p_153133_, ServerLevel p_153134_, BlockPos p_153135_, RandomSource p_153136_) {
        AetherFarmlandBlock.turnToDirt(p_153133_, p_153134_, p_153135_);
    }
}
