package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.BlockBanRecipe;
import com.gildedgames.aether.common.recipe.ItemBanRecipe;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.PlacementBanRecipeSerializer;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class BlockBanRecipeBuilder extends PlacementBanBuilder {
    private final BlockStateIngredient ingredient;

    public BlockBanRecipeBuilder(BlockStateIngredient ingredient, @Nullable BlockStateIngredient bypassBlock, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        super(bypassBlock, dimensionTypeKey, dimensionTypeTag, serializer);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, @Nullable ResourceKey<DimensionType> dimensionTypeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, BlockStateIngredient.EMPTY, dimensionTypeKey, null, serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, @Nullable TagKey<DimensionType> dimensionTypeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, BlockStateIngredient.EMPTY, null, dimensionTypeTag, serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<DimensionType> dimensionTypeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, dimensionTypeKey, null, serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable TagKey<DimensionType> dimensionTypeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, null, dimensionTypeTag, serializer);
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
        return new BlockBanRecipeBuilder(ingredient, bypassBlock, dimensionTypeKey, dimensionTypeTag, serializer);
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        finishedRecipeConsumer.accept(new BlockBanRecipeBuilder.Result(recipeId, this.getDimensionTypeKey(), this.getDimensionTypeTag(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
    }

    public static class Result extends PlacementBanBuilder.Result {
        private final BlockStateIngredient ingredient;

        public Result(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
            super(id, dimensionTypeKey, dimensionTypeTag, bypassBlock, serializer);
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(@Nonnull JsonObject json) {
            super.serializeRecipeData(json);
            json.add("ingredient", this.ingredient.toJson());
        }
    }
}
