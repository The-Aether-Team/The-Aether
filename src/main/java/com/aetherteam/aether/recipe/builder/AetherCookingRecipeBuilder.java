package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;

/**
 * [CODE COPY] - {@link net.minecraft.data.recipes.SimpleCookingRecipeBuilder}.<br><br>
 * Cleaned up, removed unnecessary static builder methods, and added support for Aether recipe categories.
 */
public class AetherCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final AetherBookCategory bookCategory;
    private final Item result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

    public AetherCookingRecipeBuilder(RecipeCategory category, AetherBookCategory bookCategory, ItemLike result, Ingredient ingredient, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result.asItem();
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.serializer = serializer;
    }

    public static AetherCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int cookingTime, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        return new AetherCookingRecipeBuilder(category, determineRecipeCategory(serializer, category), result, ingredient, experience, cookingTime, serializer);
    }

    @Override
    public AetherCookingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public AetherCookingRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);
        return this;
    }

    @Override
    public void save(RecipeOutput finishedRecipeConsumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(new AetherCookingRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
    }

    private static AetherBookCategory determineRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, RecipeCategory category) {
        AetherBookCategory bookCategory;
        if (serializer == AetherRecipeSerializers.ENCHANTING.get()) {
            switch(category) {
                case BUILDING_BLOCKS, DECORATIONS -> bookCategory = AetherBookCategory.ENCHANTING_BLOCKS;
                case FOOD -> bookCategory = AetherBookCategory.ENCHANTING_FOOD;
                default -> bookCategory = AetherBookCategory.ENCHANTING_MISC;
            }
            return bookCategory;
        } else if (serializer == AetherRecipeSerializers.REPAIRING.get()) {
            return AetherBookCategory.ENCHANTING_REPAIR;
        } else if (serializer == AetherRecipeSerializers.FREEZING.get()) {
            switch(category) {
                case BUILDING_BLOCKS, DECORATIONS -> bookCategory = AetherBookCategory.FREEZABLE_BLOCKS;
                default -> bookCategory = AetherBookCategory.FREEZABLE_MISC;
            }
            return bookCategory;
        } else {
            throw new IllegalStateException("Unknown cooking recipe type; may not belong to the Aether");
        }
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final AetherBookCategory category;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation id, String group, AetherBookCategory category, Ingredient ingredient, Item result, float experience, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<? extends AbstractCookingRecipe> serializer) {
            this.id = id;
            this.group = group;
            this.category = category;
            this.ingredient = ingredient;
            this.result = result;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.serializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("category", this.category.getSerializedName());
            json.add("ingredient", this.ingredient.toJson());
            ResourceLocation itemLocation = ForgeRegistries.ITEMS.getKey(this.result);
            if (itemLocation != null) {
                json.addProperty("result", itemLocation.toString());
            } else {
                throw new IllegalStateException("Item: " + this.result + " does not exist");
            }
            json.addProperty("experience", this.experience);
            json.addProperty("cookingtime", this.cookingTime);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
