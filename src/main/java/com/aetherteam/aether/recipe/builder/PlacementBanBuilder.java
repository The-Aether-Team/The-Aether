package com.aetherteam.aether.recipe.builder;

import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class PlacementBanBuilder implements RecipeBuilder {
    private final Optional<BlockStateIngredient> bypassBlock;
    private final Either<ResourceKey<Biome>, TagKey<Biome>> biome;
    private final RecipeSerializer<?> serializer;

    public PlacementBanBuilder(Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome, RecipeSerializer<?> serializer) {
        this.bypassBlock = bypassBlock;
        this.biome = biome;
        this.serializer = serializer;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    public Optional<BlockStateIngredient> getBypassBlock() {
        return this.bypassBlock;
    }

    public Either<ResourceKey<Biome>, TagKey<Biome>> getBiome() {
        return this.biome;
    }

    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Either<ResourceKey<Biome>, TagKey<Biome>> biome;
        private final Optional<BlockStateIngredient> bypassBlock;
        private final RecipeSerializer<?> serializer;

        public Result(ResourceLocation id, Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, RecipeSerializer<?> serializer) {
            this.id = id;
            this.biome = biome;
            this.bypassBlock = bypassBlock;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            BlockStateRecipeUtil.biomeKeyToJson(json, this.biome.left());
            BlockStateRecipeUtil.biomeTagToJson(json, this.biome.right());
            this.bypassBlock.ifPresent((blockStateIngredient) -> json.add("bypass", blockStateIngredient.toJson(false)));
        }

        @Override
        public ResourceLocation id() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> type() {
            return this.serializer;
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return null;
        }
    }
}
