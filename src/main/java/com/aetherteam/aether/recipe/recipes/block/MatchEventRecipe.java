package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.event.dispatch.AetherEventDispatch;
import com.aetherteam.aether.event.events.ItemUseConvertEvent;
import com.aetherteam.aether.recipe.BlockStateRecipeUtil;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface MatchEventRecipe {
    /**
     * Replaces an old {@link BlockState} with a new one. Also executes a mcfunction if the recipe has one.
     * @param player The {@link Player} performing the recipe.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param stack The {@link ItemStack} being used to perform the recipe.
     * @param oldState The original {@link BlockState} being interacted with.
     * @param newState The resulting {@link BlockState} from the recipe.
     * @param function The {@link CommandFunction.CacheableFunction} to run when the recipe is performed.
     * @return Whether the new {@link BlockState} was set.
     */
    default boolean convert(Level level, BlockPos pos, BlockState newState, CommandFunction.CacheableFunction function) {
        level.setBlockAndUpdate(pos, newState);
        BlockStateRecipeUtil.executeFunction(level, pos, function);
        return true;
    }

    /**
     * Checks if {@link ItemUseConvertEvent} is cancelled through {@link AetherEventDispatch#onItemUseConvert(Player, LevelAccessor, BlockPos, ItemStack, BlockState, BlockState, RecipeType)}.
     * @param player The {@link Player} performing the recipe.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param stack The {@link ItemStack} being used to perform the recipe.
     * @param oldState The original {@link BlockState} being interacted with.
     * @param newState The resulting {@link BlockState} from the recipe.
     * @return Whether {@link ItemUseConvertEvent} is cancelled.
     */
    default boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState, RecipeType recipeType) {
        ItemUseConvertEvent event = AetherEventDispatch.onItemUseConvert(player, level, pos, stack, oldState, newState, recipeType);
        return !event.isCanceled();
    }
}
