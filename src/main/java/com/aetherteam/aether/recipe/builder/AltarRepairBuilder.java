package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AltarRepairBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Ingredient ingredient;
    private final int repairTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    @Nullable
    private String group;
    private final RecipeSerializer<AltarRepairRecipe> serializer;

    private AltarRepairBuilder(RecipeCategory category, Ingredient ingredient, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        this.category = category;
        this.ingredient = ingredient;
        this.repairTime = repairTime;
        this.serializer = serializer;
    }

    public static AltarRepairBuilder repair(Ingredient item, RecipeCategory category, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
        return new AltarRepairBuilder(category, item, repairTime, serializer);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.ingredient.getItems()[0].getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder advancement$builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancement$builder);
        this.criteria.forEach(advancement$builder::addCriterion);
        recipeOutput.accept(new AltarRepairBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.repairTime, advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")), this.serializer));

    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final int repairTime;
        private final AdvancementHolder advancement;
        private final RecipeSerializer<AltarRepairRecipe> serializer;

        public Result(ResourceLocation id, String group, Ingredient ingredient, int repairTime, AdvancementHolder advancement, RecipeSerializer<AltarRepairRecipe> serializer) {
            this.id = id;
            this.group = group;
            this.ingredient = ingredient;
            this.repairTime = repairTime;
            this.advancement = advancement;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.add("ingredient", this.ingredient.toJson(false));
            json.addProperty("repairTime", this.repairTime);
        }

        @Override
        public ResourceLocation id() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> type() {
            return this.serializer;
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public AdvancementHolder advancement() {
            return advancement;
        }
    }
}
