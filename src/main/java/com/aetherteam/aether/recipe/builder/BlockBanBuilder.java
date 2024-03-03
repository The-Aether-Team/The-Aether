package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class BlockBanBuilder extends PlacementBanBuilder {
    private final BlockStateIngredient ingredient;

    public BlockBanBuilder(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        super(bypassBlock, biome, serializer);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return new BlockBanBuilder(ingredient, bypassBlock, biome, serializer);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        recipeOutput.accept(new BlockBanBuilder.Result(resourceLocation, this.getBiome(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
    }

    public static class Result extends PlacementBanBuilder.Result {
        private final BlockStateIngredient ingredient;

        public Result(ResourceLocation id, Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
            super(id, biome, bypassBlock, serializer);
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.add("ingredient", this.ingredient.toJson(false));
        }
    }
}