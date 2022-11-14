package com.gildedgames.aether.block;

import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.recipes.block.IcestoneFreezableRecipe;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

import java.util.*;

public interface FreezingBlock extends FreezingBehavior<BlockState> {
    /**
     * This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function.
     */
    float SQRT_8 = Mth.sqrt(8);

    /**
     * Table of cached {@link AetherRecipeTypes#ICESTONE_FREEZABLE} recipes, searchable with associated {@link Block}s and {@link BlockPropertyPair}s.
     */
    Table<Block, BlockPropertyPair, IcestoneFreezableRecipe> cachedBlocks = HashBasedTable.create();

    /**
     * Freezes blocks from one block to another using the {@link AetherRecipeTypes#ICESTONE_FREEZABLE} recipe type.
     * @param level The {@link Level} to freeze the blocks in.
     * @param pos The {@link BlockPos} the freezing occurred at.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param flag The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, BlockState source, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            Block oldBlock = oldBlockState.getBlock();
            FluidState fluidState = level.getFluidState(pos);
            if (!fluidState.isEmpty() && oldBlockState == source && oldBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) { // Removes water from waterlogged blocks.
                BlockState fluidBlockState = fluidState.createLegacyBlock();
                Block fluidBlock = fluidBlockState.getBlock();
                BlockPropertyPair pair = matchesCache(fluidBlock, fluidBlockState);
                if (pair != null) {
                    IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(fluidBlock, pair);
                    if (freezableRecipe != null) {
                        level.setBlock(pos, source.setValue(BlockStateProperties.WATERLOGGED, false), flag);
                    }
                }
            } else {
                if (oldBlockState.getFluidState().isEmpty()) { // Default freezing behavior.
                    BlockPropertyPair pair = matchesCache(oldBlock, oldBlockState);
                    if (pair != null) {
                        IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(oldBlock, pair);
                        if (freezableRecipe != null) {
                            BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                            return this.freezeBlockAt(level, pos, oldBlockState, newBlockState, source, flag);
                        }
                    }
                } else { // Breaks a block before freezing if it has a FluidState attached by default (this is different from waterlogging for blocks like Kelp and Seagrass).
                    oldBlockState = oldBlockState.getFluidState().createLegacyBlock();
                    BlockPropertyPair pair = matchesCache(oldBlock, oldBlockState);
                    if (pair != null) {
                        IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(oldBlock, pair);
                        if (freezableRecipe != null) {
                            level.destroyBlock(pos, true);
                            BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                            return this.freezeBlockAt(level, pos, oldBlockState, newBlockState, source, flag);
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockState oldBlockState, BlockState newBlockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(level, pos, oldBlockState, newBlockState, source);
    }

    /**
     * Caches the {@link AetherRecipeTypes#ICESTONE_FREEZABLE} recipes through the level's {@link net.minecraft.world.item.crafting.RecipeManager}.
     * @param level The {@link Level} that the recipe occurs in.
     */
    static void cacheRecipes(Level level) {
        if (FreezingBlock.cachedBlocks.isEmpty()) {
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ICESTONE_FREEZABLE.get())) {
                if (recipe instanceof IcestoneFreezableRecipe freezableRecipe) {
                    BlockPropertyPair[] pairs = freezableRecipe.getIngredient().getPairs();
                    if (pairs != null) {
                        Arrays.stream(pairs).forEach(pair -> cachedBlocks.put(pair.block(), pair, freezableRecipe));
                    }
                }
            }
        }
    }

    /**
     * Checks if the given block is cached. If it is, then it continues on to loop through associated {@link BlockPropertyPair}s to check if the BlockState matches with one of them. If it does, then it returns the matching pair.
     * @param block The {@link Block} to check.
     * @param blockState The {@link BlockState} to check.
     * @return A cached {@link BlockPropertyPair}, or null if there was no matching pair.
     */
    static BlockPropertyPair matchesCache(Block block, BlockState blockState) {
        if (cachedBlocks.containsRow(block)) {
            BlockPropertyPair pair = null;
            for (Map.Entry<BlockPropertyPair, IcestoneFreezableRecipe> entry : cachedBlocks.row(block).entrySet()) {
                if (entry.getKey().matches(blockState)) {
                    pair = entry.getKey();
                }
            }
            return pair;
        }
        return null;
    }
}
