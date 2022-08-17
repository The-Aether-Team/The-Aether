package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.ItemUseConvertEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface MatchEventRecipe {
    default boolean convert(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        if (this.matches(player, level, pos, stack, oldState, newState)) {
            level.setBlockAndUpdate(pos, newState);
            return true;
        }
        return false;
    }

    default boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        ItemUseConvertEvent event = AetherEventDispatch.onItemUseConvert(player, level, pos, stack, oldState, newState);
        return !event.isCanceled();
    }
}
