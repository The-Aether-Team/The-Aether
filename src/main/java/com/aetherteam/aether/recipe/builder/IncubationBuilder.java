package com.aetherteam.aether.recipe.builder;

//import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
//import com.google.gson.JsonObject;
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.CriterionTriggerInstance;
//import net.minecraft.advancements.RequirementsStrategy;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.data.recipes.RecipeBuilder;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//
//import javax.annotation.Nullable;
//import java.util.function.Consumer;
//
//public class IncubationBuilder implements RecipeBuilder {
//    private final Ingredient ingredient;
//    private final EntityType<?> entity;
//    @Nullable
//    private final CompoundTag tag;
//    private final int incubationTime;
//    private final Advancement.Builder advancement = Advancement.Builder.advancement();
//    @Nullable
//    private String group;
//    private final RecipeSerializer<IncubationRecipe> serializer;
//
//    public IncubationBuilder(Ingredient ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int incubationTime, RecipeSerializer<IncubationRecipe> serializer) {
//        this.ingredient = ingredient;
//        this.entity = entity;
//        this.tag = tag;
//        this.incubationTime = incubationTime;
//        this.serializer = serializer;
//    }
//
//    public static IncubationBuilder incubation(Ingredient ingredient, EntityType<?> entity, CompoundTag tag, int incubationTime, RecipeSerializer<IncubationRecipe> serializer) {
//        return new IncubationBuilder(ingredient, entity, tag, incubationTime, serializer);
//    }
//
//    @Override
//    public IncubationBuilder group(@Nullable String group) {
//        this.group = group;
//        return this;
//    }
//
//    @Override
//    public Item getResult() {
//        return Items.AIR;
//    }
//
//    @Override
//    public IncubationBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
//        this.advancement.addCriterion(criterionName, criterionTrigger);
//        return this;
//    }
//
//    @Override
//    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
//        this.ensureValid(id);
//        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
//        consumer.accept(new IncubationBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.entity, this.tag, this.incubationTime, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/incubation/" + id.getPath()), this.serializer));
//    }
//
//    private void ensureValid(ResourceLocation id) {
//        if (this.advancement.getCriteria().isEmpty()) {
//            throw new IllegalStateException("No way of obtaining recipe " + id);
//        }
//    }
//
//    public static class Result implements FinishedRecipe {
//        private final ResourceLocation id;
//        private final String group;
//        private final Ingredient ingredient;
//        private final EntityType<?> entity;
//        @Nullable
//        private final CompoundTag tag;
//        private final int incubationTime;
//        private final Advancement.Builder advancement;
//        private final ResourceLocation advancementId;
//        private final RecipeSerializer<IncubationRecipe> serializer;
//
//        public Result(ResourceLocation id, String group, Ingredient ingredient, EntityType<?> entity, @Nullable CompoundTag tag, int incubationTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<IncubationRecipe> serializer) {
//            this.id = id;
//            this.group = group;
//            this.ingredient = ingredient;
//            this.entity = entity;
//            this.tag = tag;
//            this.incubationTime = incubationTime;
//            this.advancement = advancement;
//            this.advancementId = advancementId;
//            this.serializer = serializer;
//        }
//
//        @Override
//        public void serializeRecipeData(JsonObject json) {
//            if (!this.group.isEmpty()) {
//                json.addProperty("group", this.group);
//            }
//            json.add("ingredient", this.ingredient.toJson());
//            json.addProperty("entity", EntityType.getKey(this.entity).toString());
//            if (this.tag != null && !this.tag.isEmpty()) {
//                json.addProperty("tag", this.tag.toString());
//            }
//            json.addProperty("incubationtime", this.incubationTime);
//        }
//
//        @Override
//        public RecipeSerializer<?> getType() {
//            return this.serializer;
//        }
//
//        @Override
//        public ResourceLocation getId() {
//            return this.id;
//        }
//
//        @Nullable
//        @Override
//        public JsonObject serializeAdvancement() {
//            return this.advancement.serializeToJson();
//        }
//
//        @Nullable
//        @Override
//        public ResourceLocation getAdvancementId() {
//            return this.advancementId;
//        }
//    }
//}
