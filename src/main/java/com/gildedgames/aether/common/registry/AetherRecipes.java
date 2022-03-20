package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.gildedgames.aether.common.recipe.EnchantingRecipe;
import com.gildedgames.aether.common.recipe.FreezingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

	@Mod.EventBusSubscriber(modid = Aether.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RecipeTypes
	{
		public static RecipeType<EnchantingRecipe> ENCHANTING;
		public static RecipeType<FreezingRecipe> FREEZING;

		@SubscribeEvent
		public static void registerRecipeType(RegistryEvent.Register<Block> event) {
			// Forge does not include a registry for RecipeTypes, and starting from 1.18.2,
			// registering in a vanilla registry must be done in any registry event.
			ENCHANTING = RecipeType.register(Aether.MODID + ":enchanting");
			FREEZING = RecipeType.register(Aether.MODID + ":freezing");
			Aether.LOGGER.info("Registered " + Aether.MODID + " recipe types.");
		}
	}
}
