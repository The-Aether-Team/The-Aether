package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class AmbrosiumRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public AmbrosiumRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable CommandFunction.CacheableFunction function) {
        super(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get(), id, ingredient, result, function);
    }

    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.AMBROSIUM_ENCHANTING.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<AmbrosiumRecipe> {
        public Serializer() {
            super(AmbrosiumRecipe::new);
        }
    }
}