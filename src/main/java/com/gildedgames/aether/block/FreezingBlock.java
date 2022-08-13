package com.gildedgames.aether.block;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.recipes.block.IcestoneFreezableRecipe;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.util.Mth;
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
    Table<Block, BlockPropertyPair, IcestoneFreezableRecipe> cachedBlocks = HashBasedTable.create();
    // This magic number comes from b1.7.3 code that checks if the Euclidean distance of a coordinate exceeds 8 for a spherical function
    float SQRT_8 = Mth.sqrt(8);

    default int freezeBlocks(Level worldIn, BlockPos origin, BlockState source, float radius) {
        return FreezingBehavior.super.freezeBlocks(worldIn, origin, source, radius);
    }

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

    @Override
    default int freezeFromRecipe(Level level, BlockState source, BlockPos pos, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            Block oldBlock = oldBlockState.getBlock();
            FluidState fluidState = level.getFluidState(pos);
            if (!fluidState.isEmpty() && oldBlockState == source) {
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
                BlockPropertyPair pair = matchesCache(oldBlock, oldBlockState);
                if (pair != null) {
                    IcestoneFreezableRecipe freezableRecipe = cachedBlocks.get(oldBlock, pair);
                    if (freezableRecipe != null) {
                        BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                        return this.freezeBlockAt(level, source, oldBlockState, newBlockState, pos, flag);
                    }
                }
            }
        }
        return 0;
    }

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

    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, BlockState source) {
        return AetherEventDispatch.onBlockFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
