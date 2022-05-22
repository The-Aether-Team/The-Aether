package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AltarRepairBuilder implements RecipeBuilder
{
    private final Ingredient ingredient;
    private final int repairTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<AltarRepairRecipe> serializer;

    private AltarRepairBuilder(Ingredient ingredient, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        this.ingredient = ingredient;
        this.repairTime = repairTime;
        this.serializer = serializer;
    }

    public static AltarRepairBuilder repair(Ingredient item, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        return new AltarRepairBuilder(item, repairTime, serializer);
    }

    @Override
    @Nonnull
    public AltarRepairBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    @Nonnull
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    @Nonnull
    public Item getResult() {
        return this.ingredient.getItems()[0].getItem();
    }

    public void save(Consumer<FinishedRecipe> consumer, @Nonnull ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        consumer.accept(new AltarRepairBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.repairTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + this.ingredient.getItems()[0].getItem().getItemCategory().getRecipeFolderName() + "/" + pRecipeId.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation p_218634_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_218634_1_);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final int repairTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<AltarRepairRecipe> serializer;

        public Result(ResourceLocation recipeLocation, String pGroup, Ingredient ingredient, int repairTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<AltarRepairRecipe> serializer) {
            this.id = recipeLocation;
            this.group = pGroup;
            this.ingredient = ingredient;
            this.repairTime = repairTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.addProperty("repairTime", this.repairTime);
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
