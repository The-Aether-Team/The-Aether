package com.gildedgames.aether.common.item.accessories.abilities;

import com.gildedgames.aether.common.block.util.FreezingBehavior;
import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface FreezingAccessory extends FreezingBehavior<ItemStack>
{
    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack source) {
        return AetherEventHooks.onItemFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
