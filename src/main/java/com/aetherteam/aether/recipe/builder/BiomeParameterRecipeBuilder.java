package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BiomeParameterRecipeBuilder implements RecipeBuilder {
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;
    private final BlockPropertyPair result;
    private final BlockStateIngredient ingredient;
    private Optional<ResourceLocation> function = Optional.empty();
    private final BiomeParameterRecipeSerializer.Factory<?> factory;

    public BiomeParameterRecipeBuilder(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BiomeParameterRecipeSerializer.Factory<?> factory) {
        this.result = result;
        this.ingredient = ingredient;
        this.biome = biome;
        this.factory = factory;
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, ResourceKey<Biome> biomeKey, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, ResourceKey<Biome> biomeKey, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Reference2ObjectArrayMap<Property<?>, Comparable<?>> resultProperties, ResourceKey<Biome> biomeKey, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.ofNullable(resultProperties)), ingredient, Optional.of(Either.left(biomeKey)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block result, TagKey<Biome> biomeTag, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(result, Optional.empty()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, BlockPropertyPair resultPair, TagKey<Biome> biomeTag, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultPair.block(), resultPair.properties()), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockStateIngredient ingredient, Block resultBlock, Reference2ObjectArrayMap<Property<?>, Comparable<?>> resultProperties, TagKey<Biome> biomeTag, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return recipe(BlockPropertyPair.of(resultBlock, Optional.of(resultProperties)), ingredient, Optional.of(Either.right(biomeTag)), factory);
    }

    public static BiomeParameterRecipeBuilder recipe(BlockPropertyPair result, BlockStateIngredient ingredient, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BiomeParameterRecipeSerializer.Factory<?> factory) {
        return new BiomeParameterRecipeBuilder(result, ingredient, biome, factory);
    }

    public RecipeBuilder function(Optional<ResourceLocation> function) {
        this.function = function;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        AbstractBiomeParameterRecipe recipe = this.factory.create(this.biome, this.ingredient, this.result, this.function);
        recipeOutput.accept(id, recipe, null);
    }
}
