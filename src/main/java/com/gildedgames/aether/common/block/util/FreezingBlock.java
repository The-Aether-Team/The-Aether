package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.IcestoneFreezableRecipe;
import com.gildedgames.aether.common.registry.AetherRecipes;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public interface FreezingBlock extends FreezingBehavior<BlockState> {
    // This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function
    float SQRT_8 = Mth.sqrt(8);

    @Override
    default int freezeFromRecipe(Level level, BlockState source, BlockPos pos, int flag) {
        BlockState oldBlockState = level.getBlockState(pos);
        for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipes.RecipeTypes.ICESTONE_FREEZABLE)) {
            if (recipe instanceof IcestoneFreezableRecipe freezableRecipe) {
                if (freezableRecipe.matches(level, pos, oldBlockState)) {
                    BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                    return this.freezeBlockAt(level, source, oldBlockState, newBlockState, pos, flag);
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
