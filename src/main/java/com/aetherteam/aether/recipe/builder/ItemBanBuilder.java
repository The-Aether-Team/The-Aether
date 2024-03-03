package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class ItemBanBuilder extends PlacementBanBuilder {
    private final Ingredient ingredient;

    public ItemBanBuilder(Ingredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        super(bypassBlock, biome, serializer);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome, PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> serializer) {
        return new ItemBanBuilder(ingredient, bypassBlock, biome, serializer);
    }

    @Override
    public void save(RecipeOutput finishedRecipeConsumer, ResourceLocation id) {
        finishedRecipeConsumer.accept(new ItemBanBuilder.Result(id, this.getBiome(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
    }

    public static class Result extends PlacementBanBuilder.Result {
        private final Ingredient ingredient;

        public Result(ResourceLocation id, Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, Ingredient ingredient, RecipeSerializer<?> serializer) {
            super(id, biome, bypassBlock, serializer);
            this.ingredient = ingredient;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.add("ingredient", this.ingredient.toJson(false));
        }
    }
}
