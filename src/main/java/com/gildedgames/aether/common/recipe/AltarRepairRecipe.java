package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AltarRepairRecipe implements IRecipe<IInventory>
{
    public final ResourceLocation id;
    public final Ingredient ingredient;
    public final int cookingTime;

    public AltarRepairRecipe(ResourceLocation recipeLocation, Ingredient ingredient, int cookingTime) {
        this.id = recipeLocation;
        this.ingredient = ingredient;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        return this.ingredient.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(IInventory inventory) {
        //TODO: See repair code. keep nbt, etc.
        return this.ingredient.getItems()[0];
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public ItemStack getResultItem() {
        return this.ingredient.getItems()[0];
    }

    public int getRepairingTime() {
        return this.cookingTime;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(AetherBlocks.ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return AetherRecipes.REPAIRING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return AetherRecipes.RecipeTypes.REPAIRING;
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
