package com.aetherteam.aether.item.materials.behavior;

import com.aetherteam.aether.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.recipes.BlockStateRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ItemUseConversion<R extends MatchEventRecipe & BlockStateRecipe> {
    /**
     * Runs block conversion code using the given {@link RecipeType}.
     * @param recipeType The {@link RecipeType RecipeType&lt;T&gt;} to lookup recipes from.
     *                   &lt;T&gt; extends &lt;R&gt;, which extends {@link MatchEventRecipe} and {@link BlockStateRecipe}.
     *                   The double extension is due to the type parameter that {@link RecipeType} takes.
     * @param context The {@link UseOnContext} of the conversion.
     * @return A success on client and consume on server (based on {@link InteractionResult#sidedSuccess(boolean)}) if the recipe is found, and a pass if it is not.
     * Used by the return of whatever {@link net.minecraft.world.item.Item#useOn(UseOnContext)} called this.
     */
    default <T extends R> InteractionResult convertBlock(RecipeType<T> recipeType, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = level.getBlockState(pos);

        for (RecipeHolder<T> recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) { // Gets the list of recipes existing for a RecipeType.
            if (recipe != null) {
                BlockState newState = recipe.value().getResultState(oldBlockState); // Gets the result BlockState and gives it the properties of the old BlockState
                if (recipe.value().matches(player, level, pos, heldItem, oldBlockState, newState, recipeType)) { // Checks if the recipe is actually for the oldState and if it hasn't been cancelled with an event.
                    if (!level.isClientSide() && recipe.value().convert(level, pos, newState, recipe.value().getFunction())) { // Converts the block according to the recipe on the server side.
                        if (player != null && !player.getAbilities().instabuild) { // Consumes the item being used for conversion if possible.
                            heldItem.shrink(1);
                        }
                        return InteractionResult.CONSUME;
                    } else if (level.isClientSide()) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Similar to {@link ItemUseConversion#convertBlock(RecipeType, UseOnContext)} except it is used by dispensers, i.e. without context.
     * @param recipeType The {@link RecipeType RecipeType&lt;T&gt;} to lookup recipes from.
     *                   &lt;T&gt; extends &lt;R&gt;, which extends {@link MatchEventRecipe} and {@link BlockStateRecipe}.
     *                   The double extension is due to the type parameter that {@link RecipeType} takes.
     * @param level The {@link Level} the conversion is being attempted in.
     * @param pos The {@link BlockPos} the conversion is being attempted at.
     * @param stack The {@link ItemStack} being used to attempt conversion.
     * @return A {@link Boolean} which returns true if the conversion was successful and false if not.
     */
    default <T extends R> boolean convertBlockWithoutContext(RecipeType<T> recipeType, Level level, BlockPos pos, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            for (RecipeHolder<T> recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {
                if (recipe != null) {
                    BlockState newState = recipe.value().getResultState(oldBlockState);
                    if (recipe.value().matches(null, level, pos, null, oldBlockState, newState, recipeType)) {
                        if (recipe.value().convert(level, pos, newState, recipe.value().getFunction())) {
                            stack.shrink(1);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
