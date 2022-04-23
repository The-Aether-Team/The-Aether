package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AbstractBlockStateRecipe;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class SwetBallRecipeBuilder extends BlockStateRecipeBuilder {
    private final ResourceKey<Biome> biomeKey;
    private final TagKey<Biome> biomeTag;

    public SwetBallRecipeBuilder(Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        super(resultBlock, resultProperties, ingredient, serializer);
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
    }

    public static SwetBallRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, BlockStateRecipeSerializer<?> serializer) {
        return new SwetBallRecipeBuilder(result, Map.of(), ingredient, null, null, serializer);
    }

    public static SwetBallRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, BlockStateRecipeSerializer<?> serializer) {
        return new SwetBallRecipeBuilder(result, Map.of(), ingredient, biomeKey, null, serializer);
    }

    public static SwetBallRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, BlockStateRecipeSerializer<?> serializer) {
        return new SwetBallRecipeBuilder(result, Map.of(), ingredient, null, biomeTag, serializer);
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        finishedRecipeConsumer.accept(new SwetBallRecipeBuilder.Result(recipeId, this.biomeKey, this.biomeTag, this.getIngredient(), this.getResultBlock(), this.getResultProperties(), this.getSerializer()));
    }

    public static class Result extends BlockStateRecipeBuilder.Result {
        private final ResourceKey<Biome> biomeKey;
        private final TagKey<Biome> biomeTag;

        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            super(id, ingredient, resultBlock, resultProperties, serializer);
            this.biomeKey = biomeKey;
            this.biomeTag = biomeTag;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            BlockStateRecipeUtil.biomeKeyToJson(json, this.biomeKey);
            BlockStateRecipeUtil.biomeTagToJson(json, this.biomeTag);
            super.serializeRecipeData(json);
        }

        public ResourceKey<Biome> getBiomeKey() {
            return this.biomeKey;
        }

        public TagKey<Biome> getBiomeTag() {
            return this.biomeTag;
        }
    }
}
