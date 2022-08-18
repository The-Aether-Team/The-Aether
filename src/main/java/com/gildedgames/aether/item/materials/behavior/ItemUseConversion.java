package com.gildedgames.aether.item.materials.behavior;

import com.gildedgames.aether.recipe.recipes.block.BlockStateRecipe;
import com.gildedgames.aether.recipe.recipes.block.MatchEventRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ItemUseConversion<R extends MatchEventRecipe & BlockStateRecipe> {
    default <T extends R> InteractionResult convertBlock(RecipeType<T> recipeType, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = level.getBlockState(pos);

        for (R recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {
            if (recipe != null) {
                BlockState newState = recipe.getResultState(oldBlockState);
                if (recipe.matches(player, level, pos, heldItem, oldBlockState, newState)) {
                    if (!level.isClientSide() && recipe.convert(player, level, pos, heldItem, oldBlockState, newState)) {
                        if (player != null && !player.getAbilities().instabuild) {
                            heldItem.shrink(1);
                        }
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }
        return InteractionResult.PASS;
    }

    default <T extends R> boolean convertBlockWithoutContext(RecipeType<T> recipeType, Level level, BlockPos pos, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            for (R recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {
                if (recipe != null) {
                    if (recipe.convert(null, level, pos, null, oldBlockState, recipe.getResultState(oldBlockState))) {
                        stack.shrink(1);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
