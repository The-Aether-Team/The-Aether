package com.gildedgames.aether.common.item.accessories.abilities;

import com.gildedgames.aether.common.block.util.FreezingBehavior;
import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.AccessoryFreezableRecipe;
import com.gildedgames.aether.common.registry.AetherRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    @Override
    default int freezeFromRecipe(Level level, ItemStack source, BlockPos pos, int flag) {
        BlockState oldBlockState = level.getBlockState(pos);
        for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipes.RecipeTypes.ACCESSORY_FREEZABLE)) {
            if (recipe instanceof AccessoryFreezableRecipe freezableRecipe) {
                if (freezableRecipe.matches(level, pos, oldBlockState)) {
                    BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                    return this.freezeBlockAt(level, source, oldBlockState, newBlockState, pos, flag);
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
