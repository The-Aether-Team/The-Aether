package com.gildedgames.aether.recipe.recipes.item;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.recipe.AetherBookCategory;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AltarRepairRecipe extends AbstractAetherCookingRecipe {
    public final Ingredient ingredient;

    public AltarRepairRecipe(ResourceLocation recipeLocation, String groupIn, AetherBookCategory category, Ingredient ingredient, int cookingTime) {
        super(AetherRecipeTypes.ENCHANTING.get(), recipeLocation, groupIn, category, ingredient, ingredient.getItems()[0], 0.0F, cookingTime);
        this.ingredient = ingredient;
    }

    @Override
    public ItemStack assemble(Container inventory) {
        return this.ingredient.getItems()[0];
    }

    @Override
    public ItemStack getResultItem() {
        return this.ingredient.getItems()[0];
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.ALTAR.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.ENCHANTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return AetherRecipeTypes.ENCHANTING.get();
    }

    public static class Serializer implements RecipeSerializer<AltarRepairRecipe>
    {
        public AltarRepairRecipe fromJson(ResourceLocation recipeLocation, JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");
            AetherBookCategory aetherBookCategory = AetherBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", null), AetherBookCategory.MISC);
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
            int cookingTime = GsonHelper.getAsInt(jsonObject, "repairTime", 200);
            return new AltarRepairRecipe(recipeLocation, group, aetherBookCategory, ingredient, cookingTime);
        }

        public AltarRepairRecipe fromNetwork(ResourceLocation recipeLocation, FriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            AetherBookCategory aetherBookCategory = buffer.readEnum(AetherBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(recipeLocation, group, aetherBookCategory, ingredient, cookingTime);
        }

        public void toNetwork(FriendlyByteBuf buffer, AltarRepairRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.aetherCategory());
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.cookingTime);
        }
    }
}
