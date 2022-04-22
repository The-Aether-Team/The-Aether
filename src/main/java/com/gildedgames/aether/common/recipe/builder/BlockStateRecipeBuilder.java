package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AbstractBlockStateRecipe;
import com.gildedgames.aether.common.recipe.util.BlockPropertyPair;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public class BlockStateRecipeBuilder implements RecipeBuilder {
    private final Block resultBlock;
    private final Map<Property<?>, Comparable<?>> resultProperties;
    private final BlockStateIngredient ingredient;
    private final BlockStateRecipeSerializer<?> serializer;

    private BlockStateRecipeBuilder(Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, BlockStateIngredient ingredient, BlockStateRecipeSerializer<?> serializer) {
        this.resultBlock = resultBlock;
        this.resultProperties = resultProperties;
        this.ingredient = ingredient;
        this.serializer = serializer;
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(resultPair.block(), resultPair.properties(), ingredient, serializer);
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(resultBlock, resultProperties, ingredient, serializer);
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(resultBlock, Map.of(), ingredient, serializer);
    }

    @Nonnull
    @Override
    public RecipeBuilder unlockedBy(@Nonnull String criterionName, @Nonnull CriterionTriggerInstance criterionTriggerInstance) {
        return this;
    }

    @Nonnull
    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Nonnull
    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        finishedRecipeConsumer.accept(new BlockStateRecipeBuilder.Result(recipeId, this.ingredient, this.resultBlock, this.resultProperties, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final BlockStateIngredient ingredient;
        private final Block resultBlock;
        private final Map<Property<?>, Comparable<?>> resultProperties;
        private final RecipeSerializer<? extends AbstractBlockStateRecipe> serializer;

        public Result(ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.resultBlock = resultBlock;
            this.resultProperties = resultProperties;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toJson());
            if (this.resultProperties.isEmpty()) {
                pJson.add("result", BlockStateIngredient.of(this.resultBlock).toJson());
            } else {
                pJson.add("result", BlockStateIngredient.of(BlockPropertyPair.of(this.resultBlock, this.resultProperties)).toJson());
            }
        }

        @Nonnull
        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Nonnull
        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
