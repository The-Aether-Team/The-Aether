package com.aetherteam.aether.block.dispenser;

import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import com.aetherteam.aether.recipe.recipes.block.MatchEventRecipe;
import com.aetherteam.nitrogen.recipe.recipes.BlockStateRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseUsableItemBehavior<R extends MatchEventRecipe & BlockStateRecipe> extends OptionalDispenseItemBehavior implements ItemUseConversion<R> {
    private final RecipeType<R> recipeType;

    public DispenseUsableItemBehavior(RecipeType<R> recipeType) {
        this.recipeType = recipeType;
    }

    /**
     * @see ItemUseConversion#convertBlockWithoutContext(RecipeType, Level, BlockPos, ItemStack)
     */
    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        this.setSuccess(true);
        Level world = source.level();
        BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        if (!this.convertBlockWithoutContext(this.recipeType, world, blockpos, stack)) {
            this.setSuccess(false);
        }
        return stack;
    }
}
