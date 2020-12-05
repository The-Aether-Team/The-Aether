package com.aether.item.crafting;

import com.aether.Aether;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class AetherRecipeTypes {

	public static final IRecipeType<EnchantingRecipe> ENCHANTING = register("enchanting");
	public static final IRecipeType<FreezingRecipe> FREEZING = register("freezing");
	
	private static <T extends IRecipe<?>> IRecipeType<T> register(String name) {
		return IRecipeType.register(Aether.MODID + ':' + name);
	}
	
}
