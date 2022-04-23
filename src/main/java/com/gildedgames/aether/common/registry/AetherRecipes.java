package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.gildedgames.aether.common.recipe.EnchantingRecipe;
import com.gildedgames.aether.common.recipe.FreezingRecipe;
import com.gildedgames.aether.common.recipe.SwetBallRecipe;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
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
	public static final RegistryObject<BlockStateRecipeSerializer<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_SERIALIZERS.register("swet_ball_conversion", SwetBallRecipe.Serializer::new);

	public static class RecipeTypes
	{
		public static RecipeType<EnchantingRecipe> ENCHANTING;
		public static RecipeType<FreezingRecipe> FREEZING;
		public static RecipeType<SwetBallRecipe> SWET_BALL_CONVERSION;

		public static void init() {

			ENCHANTING = RecipeType.register(new ResourceLocation(Aether.MODID, "enchanting").toString());
			FREEZING = RecipeType.register(new ResourceLocation(Aether.MODID, "freezing").toString());
			SWET_BALL_CONVERSION = RecipeType.register(new ResourceLocation(Aether.MODID, "swet_ball_conversion").toString());
		}
	}
}
