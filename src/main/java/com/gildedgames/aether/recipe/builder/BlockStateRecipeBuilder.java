package com.gildedgames.aether.recipe.builder;

import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.block.AbstractBlockStateRecipe;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class BlockStateRecipeBuilder implements RecipeBuilder {
    private final BlockPropertyPair result;
    private final BlockStateIngredient ingredient;
    private final BlockStateRecipeSerializer<?> serializer;

    public BlockStateRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, BlockStateRecipeSerializer<?> serializer) {
        this.result = result;
        this.ingredient = ingredient;
        this.serializer = serializer;
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, BlockStateRecipeSerializer<?> serializer) {
        return recipe(ingredient, BlockPropertyPair.of(resultBlock, Map.of()), serializer);
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties, BlockStateRecipeSerializer<?> serializer) {
        return recipe(ingredient, BlockPropertyPair.of(resultBlock, resultProperties), serializer);
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair result, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(result, ingredient, serializer);
    }

    public BlockStateIngredient getIngredient() {
        return this.ingredient;
    }

    public BlockPropertyPair getResultPair() {
        return this.result;
    }

    public BlockStateRecipeSerializer<?> getSerializer() {
        return this.serializer;
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
        finishedRecipeConsumer.accept(new BlockStateRecipeBuilder.Result(recipeId, this.ingredient, this.result, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final BlockStateIngredient ingredient;
        private final BlockPropertyPair result;
        private final RecipeSerializer<? extends AbstractBlockStateRecipe> serializer;

        public Result(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            if (this.result.properties().isEmpty()) {
                json.add("result", BlockStateIngredient.of(this.result.block()).toJson());
            } else {
                json.add("result", BlockStateIngredient.of(this.result).toJson());
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
