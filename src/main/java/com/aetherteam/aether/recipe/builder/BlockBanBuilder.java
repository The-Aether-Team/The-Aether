package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.google.gson.JsonObject;
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

    public BlockBanBuilder(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        super(bypassBlock, biomeKey, biomeTag, serializer);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, Optional.empty(), Optional.ofNullable(biomeKey), Optional.empty(), serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, Optional.empty(), Optional.empty(), Optional.ofNullable(biomeTag), serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, Optional.ofNullable(biomeKey), Optional.empty(), serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, Optional.empty(), Optional.ofNullable(biomeTag), serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return new BlockBanBuilder(ingredient, bypassBlock, biomeKey, biomeTag, serializer);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        recipeOutput.accept(new BlockBanBuilder.Result(resourceLocation, this.getBiomeKey(), this.getBiomeTag(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
    }

    public static class Result extends PlacementBanBuilder.Result {
        private final BlockStateIngredient ingredient;

        public Result(ResourceLocation id, Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, Optional<BlockStateIngredient> bypassBlock, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
            super(id, biomeKey, biomeTag, bypassBlock, serializer);
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.add("ingredient", this.ingredient.toJson(false));
        }
    }
}