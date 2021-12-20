package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.gson.JsonObject;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AltarRepairRecipe extends AbstractCookingRecipe
{
    public final Ingredient ingredient;

    public AltarRepairRecipe(ResourceLocation recipeLocation, Ingredient ingredient, int cookingTime) {
        super(AetherRecipes.RecipeTypes.ENCHANTING, recipeLocation, "", ingredient, ingredient.getItems()[0], 0.0F, cookingTime);
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
        return AetherRecipes.ENCHANTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return AetherRecipes.RecipeTypes.ENCHANTING;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AltarRepairRecipe>
    {
        public AltarRepairRecipe fromJson(ResourceLocation recipeLocation, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "ingredient"));
            int cookingTime = GsonHelper.getAsInt(jsonObject, "repairTime", 200);
            return new AltarRepairRecipe(recipeLocation, ingredient, cookingTime);
        }

        public AltarRepairRecipe fromNetwork(ResourceLocation recipeLocation, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(recipeLocation, ingredient, cookingTime);
        }

        public void toNetwork(FriendlyByteBuf buffer, AltarRepairRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.cookingTime);
        }
    }
}
