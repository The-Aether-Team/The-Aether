package com.aetherteam.aether.recipe.builder;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.builder.BlockStateRecipeBuilder;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class BiomeParameterRecipeBuilder extends BlockStateRecipeBuilder {
    @Nullable
    private final ResourceKey<Biome> biomeKey;
    @Nullable
    private final TagKey<Biome> biomeTag;

    public BiomeParameterRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        super(result, ingredient, serializer);
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(result, Map.of()), ingredient, biomeKey, null, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, biomeKey, null, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultBlock, resultProperties), ingredient, biomeKey, null, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(result, Map.of()), ingredient, null, biomeTag, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, null, biomeTag, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(BlockPropertyPair.of(resultBlock, resultProperties), ingredient, null, biomeTag, serializer);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockPropertyPair result, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return new BiomeParameterRecipeBuilder(result, ingredient, biomeKey, biomeTag, serializer);
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new BiomeParameterRecipeBuilder.Result(id, this.biomeKey, this.biomeTag, this.getIngredient(), this.getResultPair(), this.getSerializer()));
    }

    public static class Result extends BlockStateRecipeBuilder.Result {
        @Nullable
        private final ResourceKey<Biome> biomeKey;
        @Nullable
        private final TagKey<Biome> biomeTag;

        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this(id, biomeKey, biomeTag, ingredient, result, serializer, null);
        }

        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer, @Nullable ResourceLocation function) {
            super(id, ingredient, result, serializer, function);
            this.biomeKey = biomeKey;
            this.biomeTag = biomeTag;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            BlockStateRecipeUtil.biomeKeyToJson(json, this.biomeKey);
            BlockStateRecipeUtil.biomeTagToJson(json, this.biomeTag);
            super.serializeRecipeData(json);
        }
    }
}
