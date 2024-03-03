package com.aetherteam.aether.recipe.builder;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.Optional;

public class BiomeParameterRecipeBuilder extends BlockStateRecipeBuilder {
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;

    public BiomeParameterRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateRecipeSerializer<?> serializer) {
        super(result, ingredient, serializer);
        this.biome = biome;
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.left(biomeKey)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.left(biomeKey)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.ofNullable(resultProperties)), ingredient, Optional.of(Either.left(biomeKey)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.right(biomeTag)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.right(biomeTag)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.of(resultProperties)), ingredient, Optional.of(Either.right(biomeTag)), serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateRecipeSerializer<?> serializer) {
        return new BiomeParameterRecipeBuilder(result, ingredient, biome, serializer);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(new BiomeParameterRecipeBuilder.Result(id, this.biome, this.getIngredient(), this.getResultPair(), this.getSerializer()));
    }

    public static class Result extends BlockStateRecipeBuilder.Result {
        private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;

        public Result(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this(id, biome, ingredient, result, serializer, Optional.empty());
        }

        public Result(ResourceLocation id, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer, Optional<ResourceLocation> function) {
            super(id, ingredient, result, serializer, function);
            this.biome = biome;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (this.biome.isPresent()) {
                BlockStateRecipeUtil.biomeKeyToJson(json, this.biome.get().left());
                BlockStateRecipeUtil.biomeTagToJson(json, this.biome.get().right());
            }
            super.serializeRecipeData(json);
        }
    }
}
