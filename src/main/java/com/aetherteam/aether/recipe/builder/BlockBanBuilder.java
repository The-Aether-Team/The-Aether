package com.aetherteam.aether.recipe.builder;

//import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
//import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
//import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
//import com.google.gson.JsonObject;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.block.state.BlockState;
//
//import javax.annotation.Nullable;
//import java.util.function.Consumer;
//
//public class BlockBanBuilder extends PlacementBanBuilder {
//    private final BlockStateIngredient ingredient;
//
//    public BlockBanBuilder(BlockStateIngredient ingredient, @Nullable BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        super(bypassBlock, biomeKey, biomeTag, serializer);
//        this.ingredient = ingredient;
//    }
//
//    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        return recipe(ingredient, BlockStateIngredient.EMPTY, biomeKey, null, serializer);
//    }
//
//    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        return recipe(ingredient, BlockStateIngredient.EMPTY, null, biomeTag, serializer);
//    }
//
//    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        return recipe(ingredient, bypassBlock, biomeKey, null, serializer);
//    }
//
//    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        return recipe(ingredient, bypassBlock, null, biomeTag, serializer);
//    }
//
//    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> serializer) {
//        return new BlockBanBuilder(ingredient, bypassBlock, biomeKey, biomeTag, serializer);
//    }
//
//    @Override
//    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
//        finishedRecipeConsumer.accept(new BlockBanBuilder.Result(id, this.getBiomeKey(), this.getBiomeTag(), this.getBypassBlock(), this.ingredient, this.getSerializer()));
//    }
//
//    public static class Result extends PlacementBanBuilder.Result {
//        private final BlockStateIngredient ingredient;
//
//        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, @Nullable BlockStateIngredient bypassBlock, BlockStateIngredient ingredient, RecipeSerializer<?> serializer) {
//            super(id, biomeKey, biomeTag, bypassBlock, serializer);
//            this.ingredient = ingredient;
//        }
//
//        @Override
//        public void serializeRecipeData(JsonObject json) {
//            super.serializeRecipeData(json);
//            json.add("ingredient", this.ingredient.toJson());
//        }
//    }
//}