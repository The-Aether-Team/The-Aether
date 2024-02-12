package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SwetBallRecipe extends AbstractBiomeParameterRecipe implements MatchEventRecipe {
    public SwetBallRecipe(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, String function) {
        super(AetherRecipeTypes.SWET_BALL_CONVERSION.get(), biomeKey, biomeTag, ingredient, result, function);
    }

    public SwetBallRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, String function) {
        this(null, null, ingredient, result, function);
    }

    @Override
    public boolean matches(@Nullable Player player, Level level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        return this.matches(level, pos, oldState) && MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState, recipeType);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.SWET_BALL_CONVERSION.get();
    }

    public static class Serializer extends BiomeParameterRecipeSerializer<SwetBallRecipe> {
        public Serializer() {
            super(SwetBallRecipe::new, SwetBallRecipe::new);
        }
    }
}