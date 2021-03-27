package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AltarRepairRecipe extends AbstractCookingRecipe
{
    public final Ingredient ingredient;

    public AltarRepairRecipe(ResourceLocation recipeLocation, Ingredient ingredient, int cookingTime) {
        super(AetherRecipes.RecipeTypes.ENCHANTING, recipeLocation, "", ingredient, ingredient.getItems()[0], 0.0F, cookingTime);
        this.ingredient = ingredient;
    }

    @Override
    public ItemStack assemble(IInventory inventory) {
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
    public IRecipeSerializer<?> getSerializer() {
        return AetherRecipes.ENCHANTING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return AetherRecipes.RecipeTypes.ENCHANTING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AltarRepairRecipe>
    {
        public AltarRepairRecipe fromJson(ResourceLocation recipeLocation, JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "ingredient"));
            int cookingTime = JSONUtils.getAsInt(jsonObject, "repairTime", 200);
            return new AltarRepairRecipe(recipeLocation, ingredient, cookingTime);
        }

        public AltarRepairRecipe fromNetwork(ResourceLocation recipeLocation, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int cookingTime = buffer.readVarInt();
            return new AltarRepairRecipe(recipeLocation, ingredient, cookingTime);
        }

        public void toNetwork(PacketBuffer buffer, AltarRepairRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeVarInt(recipe.cookingTime);
        }
    }
}
