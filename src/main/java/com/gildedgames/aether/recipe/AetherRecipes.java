package com.gildedgames.aether.recipe;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.recipe.recipes.AltarRepairRecipe;
import com.gildedgames.aether.recipe.recipes.EnchantingRecipe;
import com.gildedgames.aether.recipe.recipes.FreezingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraftforge.registries.RegistryObject;

public class AetherRecipes
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Aether.MODID);

	public static final RegistryObject<RecipeSerializer<AltarRepairRecipe>> REPAIRING = RECIPE_SERIALIZERS.register("repairing", AltarRepairRecipe.Serializer::new);
	public static final RegistryObject<SimpleCookingSerializer<EnchantingRecipe>> ENCHANTING = RECIPE_SERIALIZERS.register("enchanting", EnchantingRecipe.Serializer::new);
	public static final RegistryObject<SimpleCookingSerializer<FreezingRecipe>> FREEZING = RECIPE_SERIALIZERS.register("freezing", FreezingRecipe.Serializer::new);

	public static class RecipeTypes {
		public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Aether.MODID);
		public static RegistryObject<RecipeType<EnchantingRecipe>> ENCHANTING = RECIPE_TYPES.register("enchanting", () -> new RecipeType<>() {
			public String toString() {
				return "enchanting";
			}
		});
		public static RegistryObject<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> new RecipeType<>() {
			public String toString() {
				return "freezing";
			}
		});

		static <T extends Recipe<?>> RecipeType<T> register(final String pIdentifier) {
			return new RecipeType<T>() {
				public String toString() {
					return pIdentifier;
				}
			};
		}
	}
}
