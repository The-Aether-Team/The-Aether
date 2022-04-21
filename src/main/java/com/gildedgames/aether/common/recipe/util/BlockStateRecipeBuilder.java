package com.gildedgames.aether.common.recipe.util;

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
import java.util.stream.Stream;

public class BlockStateRecipeBuilder implements RecipeBuilder {
    private final Block result;
    private final Map<Property<?>, Comparable<?>> properties;
    private final BlockStateIngredient ingredient;
    private final BlockStateRecipeSerializer<?> serializer;

    private BlockStateRecipeBuilder(Block result, Map<Property<?>, Comparable<?>> properties, BlockStateIngredient ingredient, BlockStateRecipeSerializer<?> serializer) {
        this.result = result;
        this.properties = properties;
        this.ingredient = ingredient;
        this.serializer = serializer;
    }

    public static BlockStateRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, Map<Property<?>, Comparable<?>> properties, BlockStateRecipeSerializer<?> serializer) {
        return new BlockStateRecipeBuilder(result, properties, ingredient, serializer);
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
        finishedRecipeConsumer.accept(new BlockStateRecipeBuilder.Result(recipeId, this.ingredient, this.result, this.properties, this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final BlockStateIngredient ingredient;
        private final Block result; //todo: convert to BlockStateIngredient.StateValue eventually.
        private final Map<Property<?>, Comparable<?>> properties;
        private final RecipeSerializer<? extends AbstractBlockStateRecipe> serializer;

        public Result(ResourceLocation id, BlockStateIngredient ingredient, Block result, Map<Property<?>, Comparable<?>> properties, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.properties = properties;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("result", BlockStateIngredient.fromValues(Stream.of(new BlockStateIngredient.StateValue(this.result, this.properties))).toJson());
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
