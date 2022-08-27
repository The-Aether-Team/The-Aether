package com.gildedgames.aether.recipe.builder;

import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class PlacementBanBuilder implements RecipeBuilder {
    private final BlockStateIngredient bypassBlock;
    private final ResourceKey<Biome> biomeKey;
    private final TagKey<Biome> biomeTag;
    private final RecipeSerializer<?> serializer;

    public PlacementBanBuilder(BlockStateIngredient bypassBlock, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, RecipeSerializer<?> serializer) {
        this.bypassBlock = bypassBlock;
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
        this.serializer = serializer;
    }

    public BlockStateIngredient getBypassBlock() {
        return this.bypassBlock;
    }

    public ResourceKey<Biome> getBiomeKey() {
        return this.biomeKey;
    }

    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
    }

    public RecipeSerializer<?> getSerializer() {
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

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ResourceKey<Biome> biomeKey;
        private final TagKey<Biome> biomeTag;
        private final BlockStateIngredient bypassBlock;
        private final RecipeSerializer<?> serializer;

        public Result(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, RecipeSerializer<?> serializer) {
            this.id = id;
            this.biomeKey = biomeKey;
            this.biomeTag = biomeTag;
            this.bypassBlock = bypassBlock;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(@Nonnull JsonObject json) {
            BlockStateRecipeUtil.biomeKeyToJson(json, this.biomeKey);
            BlockStateRecipeUtil.biomeTagToJson(json, this.biomeTag);
            if (!this.bypassBlock.isEmpty()) {
                json.add("bypass", this.bypassBlock.toJson());
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
