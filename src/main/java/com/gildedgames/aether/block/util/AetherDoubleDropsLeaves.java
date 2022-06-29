package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.StateDefinition;

public class AetherDoubleDropsLeaves extends LeavesBlock implements IAetherDoubleDropBlock
{

    public AetherDoubleDropsLeaves(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
    }
}
