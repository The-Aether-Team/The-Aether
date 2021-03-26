package com.gildedgames.aether.common.recipe.builder;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AltarRepairBuilder
{
    private final Ingredient ingredient;
    private final int repairTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final IRecipeSerializer<AltarRepairRecipe> serializer;

    private AltarRepairBuilder(Ingredient ingredient, int repairTime, IRecipeSerializer<AltarRepairRecipe> serializer) {
        this.ingredient = ingredient;
        this.repairTime = repairTime;
        this.serializer = serializer;
    }

    public static AltarRepairBuilder repair(Ingredient item, int repairTime, IRecipeSerializer<AltarRepairRecipe> serializer) {
        return new AltarRepairBuilder(item, repairTime, serializer);
    }

    public AltarRepairBuilder unlockedBy(String p_218628_1_, ICriterionInstance p_218628_2_) {
        this.advancement.addCriterion(p_218628_1_, p_218628_2_);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_218635_1_, ResourceLocation p_218635_2_) {
        this.ensureValid(p_218635_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_218635_2_)).rewards(AdvancementRewards.Builder.recipe(p_218635_2_)).requirements(IRequirementsStrategy.OR);
        p_218635_1_.accept(new AltarRepairBuilder.Result(p_218635_2_, this.ingredient, this.repairTime, this.advancement, new ResourceLocation(p_218635_2_.getNamespace(), "recipes/" + this.ingredient.getItems()[0].getItem().getItemCategory().getRecipeFolderName() + "/" + p_218635_2_.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation p_218634_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_218634_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final int repairTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<AltarRepairRecipe> serializer;

        public Result(ResourceLocation recipeLocation, Ingredient ingredient, int repairTime, Advancement.Builder advancement, ResourceLocation advancementId, IRecipeSerializer<AltarRepairRecipe> serializer) {
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

        public IRecipeSerializer<?> getType() {
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
