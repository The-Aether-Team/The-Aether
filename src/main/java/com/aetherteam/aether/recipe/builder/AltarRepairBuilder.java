package com.aetherteam.aether.recipe.builder;

//import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
//import com.google.gson.JsonObject;
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.CriterionTriggerInstance;
//import net.minecraft.advancements.RequirementsStrategy;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.data.recipes.RecipeBuilder;
//import net.minecraft.data.recipes.RecipeCategory;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//
//import javax.annotation.Nullable;
//import java.util.function.Consumer;
//
//public class AltarRepairBuilder implements RecipeBuilder {
//    private final RecipeCategory category;
//    private final Ingredient ingredient;
//    private final int repairTime;
//    private final Advancement.Builder advancement = Advancement.Builder.advancement();
//    @Nullable
//    private String group;
//    private final RecipeSerializer<AltarRepairRecipe> serializer;
//
//    private AltarRepairBuilder(RecipeCategory category, Ingredient ingredient, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
//        this.category = category;
//        this.ingredient = ingredient;
//        this.repairTime = repairTime;
//        this.serializer = serializer;
//    }
//
//    public static AltarRepairBuilder repair(Ingredient item, RecipeCategory category, int repairTime, RecipeSerializer<AltarRepairRecipe> serializer) {
//        return new AltarRepairBuilder(category, item, repairTime, serializer);
//    }
//
//    @Override
//    public RecipeBuilder group(@Nullable String group) {
//        this.group = group;
//        return this;
//    }
//
//    @Override
//    public Item getResult() {
//        return this.ingredient.getItems()[0].getItem();
//    }
//
//    @Override
//    public AltarRepairBuilder unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
//        this.advancement.addCriterion(criterionName, criterionTrigger);
//        return this;
//    }
//
//    @Override
//    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
//        this.ensureValid(id);
//        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
//        finishedRecipeConsumer.accept(new AltarRepairBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.repairTime, this.advancement, id.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
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
//        private final int repairTime;
//        private final Advancement.Builder advancement;
//        private final ResourceLocation advancementId;
//        private final RecipeSerializer<AltarRepairRecipe> serializer;
//
//        public Result(ResourceLocation id, String group, Ingredient ingredient, int repairTime, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<AltarRepairRecipe> serializer) {
//            this.id = id;
//            this.group = group;
//            this.ingredient = ingredient;
//            this.repairTime = repairTime;
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
//            json.addProperty("repairTime", this.repairTime);
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
