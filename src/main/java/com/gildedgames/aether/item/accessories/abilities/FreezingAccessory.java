package com.gildedgames.aether.item.accessories.abilities;

import com.gildedgames.aether.block.util.FreezingBehavior;
import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
