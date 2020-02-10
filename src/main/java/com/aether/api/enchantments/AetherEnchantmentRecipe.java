package com.aether.api.enchantments;

import javax.annotation.Nonnull;

import com.aether.api.internal.AbstractAetherSmeltingRecipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantmentRecipe extends AbstractAetherSmeltingRecipe<AetherEnchantmentRecipe> {

	public AetherEnchantmentRecipe(@Nonnull Block input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Block input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Block input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Item input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Item input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Item input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull ItemStack input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull ItemStack input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull Item result, int timeRequired) {
		super(new ItemStack(result), new ItemStack(result), timeRequired);
	}

	public AetherEnchantmentRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}

	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherEnchantmentRecipe;
	}
	
}
