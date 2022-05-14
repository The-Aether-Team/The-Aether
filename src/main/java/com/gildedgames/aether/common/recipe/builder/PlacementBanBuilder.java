package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
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
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class PlacementBanBuilder implements RecipeBuilder {
    private final BlockStateIngredient bypassBlock;
    private final ResourceKey<DimensionType> dimensionTypeKey;
    private final TagKey<DimensionType> dimensionTypeTag;
    private final RecipeSerializer<?> serializer;

    public PlacementBanBuilder(BlockStateIngredient bypassBlock, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, RecipeSerializer<?> serializer) {
        this.bypassBlock = bypassBlock;
        this.dimensionTypeKey = dimensionTypeKey;
        this.dimensionTypeTag = dimensionTypeTag;
        this.serializer = serializer;
    }

    public BlockStateIngredient getBypassBlock() {
        return this.bypassBlock;
    }

    public ResourceKey<DimensionType> getDimensionTypeKey() {
        return this.dimensionTypeKey;
    }

    public TagKey<DimensionType> getDimensionTypeTag() {
        return this.dimensionTypeTag;
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
        private final ResourceKey<DimensionType> dimensionTypeKey;
        private final TagKey<DimensionType> dimensionTypeTag;
        private final BlockStateIngredient bypassBlock;
        private final RecipeSerializer<?> serializer;

        public Result(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, RecipeSerializer<?> serializer) {
            this.id = id;
            this.dimensionTypeKey = dimensionTypeKey;
            this.dimensionTypeTag = dimensionTypeTag;
            this.bypassBlock = bypassBlock;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(@Nonnull JsonObject json) {
            BlockStateRecipeUtil.dimensionTypeKeyToJson(json, this.dimensionTypeKey);
            BlockStateRecipeUtil.dimensionTypeTagToJson(json, this.dimensionTypeTag);
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
