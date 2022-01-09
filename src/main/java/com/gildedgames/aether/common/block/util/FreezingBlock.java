package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
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
        return AetherEventHooks.onBlockFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
