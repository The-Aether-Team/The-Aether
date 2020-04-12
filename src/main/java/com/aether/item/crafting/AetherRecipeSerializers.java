package com.aether.item.crafting;

import com.aether.Aether;

import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public class AetherRecipeSerializers {

	public static final IRecipeSerializer<EnchantingRecipe> ENCHANTING = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = EventBusSubscriber.Bus.MOD)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
			event.getRegistry().registerAll(new IRecipeSerializer[] {
				
				recipeSerializer("enchanting", new CookingRecipeSerializer<>(EnchantingRecipe::new, 200)),
				
			});
		}
		
		private static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S recipeSerializer(String name, S recipeSerializer) {
			recipeSerializer.setRegistryName(GameData.checkPrefix(name, true));
			return recipeSerializer;
		}
		
	}
	
}
