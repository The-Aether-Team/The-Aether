package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AltarRepairBuilder
{
    private final Ingredient ingredient;
    private final int repairTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final RecipeSerializer<AltarRepairRecipe> serializer;

    private AltarRepairBuilder(Ingredient ingredient, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        this.ingredient = ingredient;
        this.repairTime = repairTime;
        this.serializer = serializer;
    }

    public static AltarRepairBuilder repair(Ingredient item, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        return new AltarRepairBuilder(item, repairTime, serializer);
    }

    public AltarRepairBuilder unlockedBy(String p_218628_1_, CriterionTriggerInstance p_218628_2_) {
        this.advancement.addCriterion(p_218628_1_, p_218628_2_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_218635_1_, ResourceLocation p_218635_2_) {
        this.ensureValid(p_218635_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_218635_2_)).rewards(AdvancementRewards.Builder.recipe(p_218635_2_)).requirements(RequirementsStrategy.OR);
        p_218635_1_.accept(new AltarRepairBuilder.Result(p_218635_2_, this.ingredient, this.repairTime, this.advancement, new ResourceLocation(p_218635_2_.getNamespace(), "recipes/" + this.ingredient.getItems()[0].getItem().getItemCategory().getRecipeFolderName() + "/" + p_218635_2_.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation p_218634_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_218634_1_);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final int repairTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<AltarRepairRecipe> serializer;

        public Result(ResourceLocation recipeLocation, Ingredient ingredient, int repairTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<AltarRepairRecipe> serializer) {
            this.id = recipeLocation;
            this.ingredient = ingredient;
            this.repairTime = repairTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        public void serializeRecipeData(JsonObject p_218610_1_) {
            p_218610_1_.add("ingredient", this.ingredient.toJson());
            p_218610_1_.addProperty("repairTime", this.repairTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

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
