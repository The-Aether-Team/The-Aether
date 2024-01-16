package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public class ItemBanBuilder extends PlacementBanBuilder {
    private final Ingredient ingredient;

    public ItemBanBuilder(Ingredient ingredient, @Nullable BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        super(bypassBlock, biomeKey, biomeTag, serializer);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, @Nullable ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return recipe(ingredient, BlockStateIngredient.EMPTY, biomeKey, null, serializer);
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return recipe(ingredient, BlockStateIngredient.EMPTY, null, biomeTag, serializer);
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, biomeKey, null, serializer);
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, BlockStateIngredient bypassBlock, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return recipe(ingredient, bypassBlock, null, biomeTag, serializer);
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return new ItemBanBuilder(ingredient, bypassBlock, biomeKey, biomeTag, serializer);
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new ItemBanBuilder.Result(id, this.getBiomeKey(), this.getBiomeTag(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
    }

    public static class Result extends PlacementBanBuilder.Result {
        private final Ingredient ingredient;

        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, @Nullable BlockStateIngredient bypassBlock, Ingredient ingredient, RecipeSerializer<?> serializer) {
            super(id, biomeKey, biomeTag, bypassBlock, serializer);
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.add("ingredient", this.ingredient.toJson());
        }
    }
}
