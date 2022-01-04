package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public interface FreezingBlock extends FreezingBehavior<BlockState>
{
    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, BlockState source) {
        return AetherEventHooks.onBlockFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
