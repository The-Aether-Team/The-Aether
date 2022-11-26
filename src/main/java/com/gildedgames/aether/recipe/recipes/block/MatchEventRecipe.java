package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.ItemUseConvertEvent;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface MatchEventRecipe {
    default boolean convert(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState, CommandFunction.CacheableFunction function) {
        if (this.matches(player, level, pos, stack, oldState, newState)) {
            level.setBlockAndUpdate(pos, newState);
            BlockStateRecipeUtil.executeFunction(level, pos, function);
            return true;
        }
        return false;
    }

    default boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        ItemUseConvertEvent event = AetherEventDispatch.onItemUseConvert(player, level, pos, stack, oldState, newState);
        return !event.isCanceled();
    }
}
