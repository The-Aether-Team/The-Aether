package com.gildedgames.aether.common.item.materials.util;

import com.gildedgames.aether.common.event.events.SwetBallConvertEvent;
import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.AbstractBlockStateRecipe;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Supplier;

public interface ISwetBallConversion {

    default InteractionResult convertBlock(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = world.getBlockState(pos);
        //BlockState newBlockState = oldBlockState;

        for (Recipe<?> recipe : world.getRecipeManager().getAllRecipesFor(AetherRecipes.RecipeTypes.SWET_BALL_CONVERSION)) {
            if (recipe instanceof AbstractBlockStateRecipe abstractBlockStateRecipe) {
                if (abstractBlockStateRecipe.set(world, pos, oldBlockState)) {
                    if (player != null && !player.getAbilities().instabuild) {
                        heldItem.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }




//        Block oldBlock = oldBlockState.getBlock();
//        if (DEFAULT_CONVERSIONS.containsKey(oldBlock)) {
//            newBlockState = DEFAULT_CONVERSIONS.get(oldBlock);
//        }
//
//        Holder<Biome> biome = world.getBiome(pos);
//        ResourceLocation biomeName = biome.value().getRegistryName();
//        if (BIOME_CONVERSIONS.containsKey(biomeName)) {
//            Pair<Block, BlockState> blockPair = BIOME_CONVERSIONS.get(biomeName);
//            if (blockPair.getKey() == oldBlockState.getBlock()) {
//                newBlockState = blockPair.getValue();
//            }
//        }
//
//        SwetBallConvertEvent event = AetherEventDispatch.onSwetBallConvert(player, world, pos, heldItem, oldBlockState, newBlockState);
//        if (!event.isCanceled()) {
//            newBlockState = event.getNewBlockState();
//            if (newBlockState != oldBlockState && world.getBlockState(pos.above()).isAir()) {
//                world.setBlockAndUpdate(pos, newBlockState);
//                if (player != null && !player.getAbilities().instabuild) {
//                    heldItem.shrink(1);
//                }
//                return InteractionResult.SUCCESS;
//            }
//        }

        return InteractionResult.PASS;
    }

    static boolean convertBlockWithoutContext(Level world, BlockPos pos, ItemStack stack) {
        BlockState oldBlockState = world.getBlockState(pos);
//        BlockState newBlockState = oldBlockState;
//
//        Block oldBlock = oldBlockState.getBlock();
//        if (DEFAULT_CONVERSIONS.containsKey(oldBlock)) {
//            newBlockState = DEFAULT_CONVERSIONS.get(oldBlock);
//        }
//
//        Holder<Biome> biome = world.getBiome(pos);
//        ResourceLocation biomeName = biome.value().getRegistryName();
//        if (BIOME_CONVERSIONS.containsKey(biomeName)) {
//            Pair<Block, BlockState> blockPair = BIOME_CONVERSIONS.get(biomeName);
//            if (blockPair.getKey() == oldBlockState.getBlock()) {
//                newBlockState = blockPair.getValue();
//            }
//        }
//
//        SwetBallConvertEvent event = AetherEventDispatch.onSwetBallConvert(null, world, pos, stack, oldBlockState, newBlockState);
//        if (!event.isCanceled()) {
//            newBlockState = event.getNewBlockState();
//            if (newBlockState != oldBlockState && world.getBlockState(pos.above()).isAir()) {
//                world.setBlockAndUpdate(pos, newBlockState);
//                stack.shrink(1);
//                return true;
//            }
//        }

        return false;
    }
}
