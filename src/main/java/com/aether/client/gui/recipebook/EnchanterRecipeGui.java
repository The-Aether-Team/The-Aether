package com.aether.client.gui.recipebook;

import java.util.Set;
import java.util.stream.Collectors;

import com.aether.api.AetherAPI;
import com.aether.api.enchantments.AetherEnchantmentFuel;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.item.Item;

public class EnchanterRecipeGui extends AbstractRecipeBookGui {

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

	@Override
	protected String func_212960_g() {
		return "gui.recipebook.toggleRecipes.aether_legacy.enchantable";
	}

	@Override
	protected Set<Item> func_212958_h() {
		return AetherAPI.getEnchantmentFuelSet().stream()
			.map(AetherEnchantmentFuel::getFuelItem)
			.collect(Collectors.toSet());
	}

}
