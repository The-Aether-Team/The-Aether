package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.gildedgames.aether.common.recipe.EnchantingRecipe;
import com.gildedgames.aether.common.recipe.FreezingRecipe;
import net.minecraft.item.crafting.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherRecipes
{
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Aether.MODID);

	public static final RegistryObject<IRecipeSerializer<AltarRepairRecipe>> REPAIRING = RECIPE_SERIALIZERS.register("repairing", AltarRepairRecipe.Serializer::new);
	public static final RegistryObject<CookingRecipeSerializer<EnchantingRecipe>> ENCHANTING = RECIPE_SERIALIZERS.register("enchanting", EnchantingRecipe.Serializer::new);
	public static final RegistryObject<CookingRecipeSerializer<FreezingRecipe>> FREEZING = RECIPE_SERIALIZERS.register("freezing", FreezingRecipe.Serializer::new);

	public static class RecipeTypes
	{
		public static final IRecipeType<EnchantingRecipe> ENCHANTING = IRecipeType.register(Aether.MODID + ":enchanting");
		public static final IRecipeType<FreezingRecipe> FREEZING = IRecipeType.register(Aether.MODID + ":freezing");
	}
}
