package com.gildedgames.aether.block.util;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.StateContainer;

public class AetherDoubleDropsLeaves extends LeavesBlock implements IAetherDoubleDropBlock
{

    public AetherDoubleDropsLeaves(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(AetherBlockStateProperties.DOUBLE_DROPS, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
    }
}
