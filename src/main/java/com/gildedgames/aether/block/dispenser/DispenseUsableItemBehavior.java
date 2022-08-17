package com.gildedgames.aether.block.dispenser;

import com.gildedgames.aether.item.materials.behavior.ItemUseConversion;
import com.gildedgames.aether.recipe.recipes.block.BlockStateRecipe;
import com.gildedgames.aether.recipe.recipes.block.MatchEventRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import javax.annotation.Nonnull;

public class DispenseUsableItemBehavior<R extends MatchEventRecipe & BlockStateRecipe> extends OptionalDispenseItemBehavior implements ItemUseConversion<R> {
    private final RecipeType<R> recipeType;

    public DispenseUsableItemBehavior(RecipeType<R> recipeType) {
        this.recipeType = recipeType;
    }

    @Nonnull
    @Override
    protected ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
        this.setSuccess(true);
        Level world = source.getLevel();
        BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        if (!this.convertBlockWithoutContext(this.recipeType, world, blockpos, stack)) {
            this.setSuccess(false);
        }
        return stack;
    }
}
