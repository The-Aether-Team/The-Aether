package com.gildedgames.aether.common.item.materials.util;

import com.gildedgames.aether.common.recipe.SwetBallRecipe;
import com.gildedgames.aether.common.registry.AetherRecipes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ISwetBallConversion {
    default InteractionResult convertBlock(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = world.getBlockState(pos);

        for (Recipe<?> recipe : world.getRecipeManager().getAllRecipesFor(AetherRecipes.RecipeTypes.SWET_BALL_CONVERSION)) {
            if (recipe instanceof SwetBallRecipe swetBallRecipe) {
                if (swetBallRecipe.set(player, world, pos, heldItem, oldBlockState)) {
                    if (player != null && !player.getAbilities().instabuild) {
                        heldItem.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    static boolean convertBlockWithoutContext(Level world, BlockPos pos, ItemStack stack) {
        BlockState oldBlockState = world.getBlockState(pos);

        for (Recipe<?> recipe : world.getRecipeManager().getAllRecipesFor(AetherRecipes.RecipeTypes.SWET_BALL_CONVERSION)) {
            if (recipe instanceof SwetBallRecipe swetBallRecipe) {
                if (swetBallRecipe.set(null, world, pos, null, oldBlockState)) {
                    stack.shrink(1);
                    return true;
                }
            }
        }
        return false;
    }
}
