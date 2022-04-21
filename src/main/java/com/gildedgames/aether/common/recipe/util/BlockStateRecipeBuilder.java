package com.gildedgames.aether.common.recipe.util;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class BlockStateRecipeBuilder implements RecipeBuilder {
    private final BlockState result;
    private final BlockStateIngredient ingredient;
    private final BlockStateRecipeSerializer<?> serializer;

    private BlockStateRecipeBuilder(BlockState result, BlockStateIngredient ingredient, BlockStateRecipeSerializer<?> serializer) {
        this.result = result;
        this.ingredient = ingredient;
        this.serializer = serializer;
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, BlockState result, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(result, ingredient, serializer);
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
        private final BlockState result;
        private final RecipeSerializer<? extends AbstractBlockStateRecipe> serializer;

        public Result(ResourceLocation id, BlockStateIngredient ingredient, BlockState result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("result", BlockStateIngredient.of(this.result).toJson());
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
