package com.aether.api.freezables;

import javax.annotation.Nonnull;

import com.aether.api.internal.AbstractAetherSmeltingRecipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezableRecipe extends AbstractAetherSmeltingRecipe<AetherFreezableRecipe> {

	public AetherFreezableRecipe(@Nonnull Block input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull Block input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull Block input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull Item input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull Item input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull Item input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull ItemStack input, @Nonnull Block output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull ItemStack input, @Nonnull Item output, int timeRequired) {
		super(input, output, timeRequired);
	}

	public AetherFreezableRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, int timeRequired) {
		super(input, output, timeRequired);
	}
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof AetherFreezableRecipe;
	}
	
}
