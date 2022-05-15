package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AbstractBlockStateRecipe;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.common.recipe.util.BlockPropertyPair;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.dimension.DimensionType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class PlacementConversionBuilder extends BlockStateRecipeBuilder {
    private final ResourceKey<DimensionType> dimensionTypeKey;
    private final TagKey<DimensionType> dimensionTypeTag;

    public PlacementConversionBuilder(Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, BlockStateIngredient ingredient, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateRecipeSerializer<?> serializer) {
        super(resultBlock, resultProperties, ingredient, serializer);
        this.dimensionTypeKey = dimensionTypeKey;
        this.dimensionTypeTag = dimensionTypeTag;
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<DimensionType> dimensionTypeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(result, Map.of(), ingredient, dimensionTypeKey, null, serializer);
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<DimensionType> dimensionTypeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(resultPair.block(), resultPair.properties(), ingredient, dimensionTypeKey, null, serializer);
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, ResourceKey<DimensionType> dimensionTypeKey, BlockStateRecipeSerializer<?> serializer) {
        return recipe(resultBlock, resultProperties, ingredient, dimensionTypeKey, null, serializer);
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<DimensionType> dimensionTypeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(result, Map.of(), ingredient, null, dimensionTypeTag, serializer);
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<DimensionType> dimensionTypeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(resultPair.block(), resultPair.properties(), ingredient, null, dimensionTypeTag, serializer);
    }

    public static PlacementConversionBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, TagKey<DimensionType> dimensionTypeTag, BlockStateRecipeSerializer<?> serializer) {
        return recipe(resultBlock, resultProperties, ingredient, null, dimensionTypeTag, serializer);
    }

    public static PlacementConversionBuilder recipe(Block result, Map<Property<?>, Comparable<?>> resultProperties, BlockStateIngredient ingredient, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateRecipeSerializer<?> serializer) {
        return new PlacementConversionBuilder(result, resultProperties, ingredient, dimensionTypeKey, dimensionTypeTag, serializer);
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        finishedRecipeConsumer.accept(new PlacementConversionBuilder.Result(recipeId, this.dimensionTypeKey, this.dimensionTypeTag, this.getIngredient(), this.getResultBlock(), this.getResultProperties(), this.getSerializer()));
    }

    public static class Result extends BlockStateRecipeBuilder.Result {
        private final ResourceKey<DimensionType> dimensionTypeKey;
        private final TagKey<DimensionType> dimensionTypeTag;

        public Result(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            super(id, ingredient, resultBlock, resultProperties, serializer);
            this.dimensionTypeKey = dimensionTypeKey;
            this.dimensionTypeTag = dimensionTypeTag;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            BlockStateRecipeUtil.dimensionTypeKeyToJson(json, this.dimensionTypeKey);
            BlockStateRecipeUtil.dimensionTypeTagToJson(json, this.dimensionTypeTag);
            super.serializeRecipeData(json);
        }
    }
}
