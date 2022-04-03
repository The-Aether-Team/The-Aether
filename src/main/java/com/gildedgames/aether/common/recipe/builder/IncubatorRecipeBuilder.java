package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.IncubatorRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Vanilla copy
 * @see net.minecraft.data.recipes.SimpleCookingRecipeBuilder
 */
public class IncubatorRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final EntityType<?> entity;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<IncubatorRecipe> serializer;

    public IncubatorRecipeBuilder(Ingredient pIngredient, EntityType<?> pEntity, float pExperience, int pCookingTime, RecipeSerializer<IncubatorRecipe> pSerializer) {
        this.ingredient = pIngredient;
        this.entity = pEntity;
        this.experience = pExperience;
        this.cookingTime = pCookingTime;
        this.serializer = pSerializer;
    }

    public EntityType<?> getEntity() {
        return entity;
    }

    @Override
    @Nonnull
    public IncubatorRecipeBuilder unlockedBy(@Nonnull String pCriterionName, @Nonnull CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    @Nonnull
    public IncubatorRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    @Nonnull
    public Item getResult() {
        return Items.AIR;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, @Nonnull ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new IncubatorRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.entity, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/incubation/" + this.entity.getRegistryName().getPath() + "/" + pRecipeId.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final EntityType<?> entity;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, EntityType<?> pEntity, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.entity = pEntity;
            this.experience = pExperience;
            this.cookingTime = pCookingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(@Nonnull JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.add("egg", this.ingredient.toJson());
            pJson.addProperty("entity", this.entity.getRegistryName().toString());
            pJson.addProperty("incubateTime", this.cookingTime);
        }

        @Nonnull
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Nonnull
        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
