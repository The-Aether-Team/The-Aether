package com.gildedgames.aether.item.materials.behavior;

import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.recipes.SwetBallRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface SwetBallConversion {
    default InteractionResult convertBlock(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = level.getBlockState(pos);

        for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.SWET_BALL_CONVERSION.get())) {
            if (recipe instanceof SwetBallRecipe swetBallRecipe) {
                if (swetBallRecipe.matches(player, level, pos, heldItem, oldBlockState, swetBallRecipe.getResultState(oldBlockState))) {
                    if (!level.isClientSide() && swetBallRecipe.convert(player, level, pos, heldItem, oldBlockState)) {
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

    static boolean convertBlockWithoutContext(Level level, BlockPos pos, ItemStack stack) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.SWET_BALL_CONVERSION.get())) {
                if (recipe instanceof SwetBallRecipe swetBallRecipe) {
                    if (swetBallRecipe.convert(null, level, pos, null, oldBlockState)) {
                        stack.shrink(1);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
