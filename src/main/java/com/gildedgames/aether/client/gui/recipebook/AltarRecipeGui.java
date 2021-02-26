package com.gildedgames.aether.client.gui.recipebook;

import java.util.Set;
import java.util.stream.Collectors;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.enchantments.AetherEnchantmentFuel;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.item.Item;

public class AltarRecipeGui extends AbstractRecipeBookGui {

	/* TODO: I don't know what to do with these!
	@Override
	protected boolean func_212962_b() {
		return this.recipeBook.isFurnaceFilteringCraftable();
	}

	@Override
	protected void func_212959_a(boolean p_212959_1_) {
		this.recipeBook.setFurnaceFilteringCraftable(p_212959_1_);
	}

	@Override
	protected boolean func_212963_d() {
		return this.recipeBook.isFurnaceGuiOpen();
	}

	@Override
	protected void func_212957_c(boolean p_212957_1_) {
		this.recipeBook.setFurnaceGuiOpen(p_212957_1_);
	}
	
	private static final String TOGGLE_RECIPE_TRANSLATION_KEY = "gui.recipebook.toggleRecipes." + Aether.MODID + ".enchantable";

	@Override
	protected String func_212960_g() {
		return TOGGLE_RECIPE_TRANSLATION_KEY;
	}
	*/

	@Override
	protected Set<Item> func_212958_h() {
		return AetherAPI.getEnchantmentFuelSet().stream()
			.map(AetherEnchantmentFuel::getFuelItem)
			.collect(Collectors.toSet());
	}

}
