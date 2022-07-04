package com.gildedgames.aether.block;

import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public interface FreezingBlock extends FreezingBehavior<BlockState>
{
    // This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function
    float SQRT_8 = Mth.sqrt(8);

    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
