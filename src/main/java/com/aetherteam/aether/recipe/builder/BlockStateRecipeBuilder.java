package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.BlockPropertyPair;
import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.block.AbstractBlockStateRecipe;
import com.aetherteam.aether.recipe.serializer.BlockStateRecipeSerializer;
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

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;

public class BlockStateRecipeBuilder implements RecipeBuilder {
    private final BlockPropertyPair result;
    private final BlockStateIngredient ingredient;
    private final BlockStateRecipeSerializer<?> serializer;
    @Nullable
    private ResourceLocation function;

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

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    public RecipeBuilder function(@Nullable ResourceLocation function) {
        this.function = function;
        return this;
    }

    public BlockPropertyPair getResultPair() {
        return this.result;
    }

    public BlockStateIngredient getIngredient() {
        return this.ingredient;
    }

    public BlockStateRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
        return this;
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new BlockStateRecipeBuilder.Result(id, this.ingredient, this.result, this.serializer, this.function));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final BlockStateIngredient ingredient;
        private final BlockPropertyPair result;
        private final RecipeSerializer<? extends AbstractBlockStateRecipe> serializer;
        @Nullable
        private final ResourceLocation function;

        public Result(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer) {
            this(id, ingredient, result, serializer, null);
        }

        public Result(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, RecipeSerializer<? extends AbstractBlockStateRecipe> serializer, @Nullable ResourceLocation function) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.serializer = serializer;
            this.function = function;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            if (this.result.properties().isEmpty()) {
                json.add("result", BlockStateIngredient.of(this.result.block()).toJson());
            } else {
                json.add("result", BlockStateIngredient.of(this.result).toJson());
            }
            if (this.function != null) {
                json.addProperty("mcfunction", this.function.toString());
            }
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

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
