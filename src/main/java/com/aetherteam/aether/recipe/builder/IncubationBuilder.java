package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class IncubationBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final EntityType<?> entity;
    @Nullable
    private final CompoundTag tag;
    private final int incubationTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private final RecipeSerializer<IncubationRecipe> serializer;

    public IncubationBuilder(Ingredient ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int incubationTime, RecipeSerializer<IncubationRecipe> serializer) {
        this.ingredient = ingredient;
        this.entity = entity;
        this.tag = tag;
        this.incubationTime = incubationTime;
        this.serializer = serializer;
    }

    public static IncubationBuilder incubation(Ingredient ingredient, EntityType<?> entity, CompoundTag tag, int incubationTime, RecipeSerializer<IncubationRecipe> serializer) {
        return new IncubationBuilder(ingredient, entity, tag, incubationTime, serializer);
    }

    @Override
    public IncubationBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public IncubationBuilder unlockedBy(String criterionName, Criterion criterionTrigger) {
        this.criteria.put(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder advancement$builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancement$builder);
        this.criteria.forEach(advancement$builder::addCriterion);
        recipeOutput.accept(new IncubationBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.entity, this.tag, this.incubationTime, advancement$builder.build(new ResourceLocation(id.getNamespace(), "recipes/incubation/" + id.getPath())), this.serializer));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public record Result(ResourceLocation id, String group, Ingredient ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int incubationTime, AdvancementHolder advancement, RecipeSerializer<IncubationRecipe> type) implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.add("ingredient", this.ingredient.toJson(false));
            json.addProperty("entity", EntityType.getKey(this.entity).toString());
            if (this.tag != null && !this.tag.isEmpty()) {
                json.addProperty("tag", this.tag.toString());
            }
            json.addProperty("incubationtime", this.incubationTime);
        }
    }
}
