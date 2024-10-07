package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
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
import java.util.Optional;

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
        recipeOutput.accept(id, new IncubationRecipe(this.group == null ? "" : this.group, this.ingredient, this.entity, Optional.ofNullable(this.tag), this.incubationTime), advancement$builder.build(ResourceLocation.withDefaultNamespace(id.getNamespace(), "recipes/incubation/" + id.getPath())));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
