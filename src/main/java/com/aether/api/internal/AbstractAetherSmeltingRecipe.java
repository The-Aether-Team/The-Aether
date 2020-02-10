package com.aether.api.internal;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class AbstractAetherSmeltingRecipe<T extends AbstractAetherSmeltingRecipe<T>> extends ForgeRegistryEntry<T> {
	private final ItemStack input, output;
	private final int timeRequired;

	public AbstractAetherSmeltingRecipe(@Nonnull ItemStack input, @Nonnull Block output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Block input, @Nonnull ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Block input, @Nonnull Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull ItemStack input, @Nonnull Item output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Item input, @Nonnull ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Item input, @Nonnull Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Block input, @Nonnull Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull Item input, @Nonnull Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AbstractAetherSmeltingRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, int timeRequired) {
		Validate.notNull(input, "input stack was null");
		Validate.notNull(output, "output stack was null");
		
		this.input = input;
		this.output = output;
		this.timeRequired = timeRequired;

		this.setRegistryName(input.getItem().getRegistryName());
	}
	
	public @Nonnull ItemStack getInput() {
		return input;
	}
	
	public @Nonnull ItemStack getOutput() {
		return output;
	}
	
	public int getTimeRequired() {
		return timeRequired;
	}
	
	protected boolean canEqual(Object obj) {
		return obj instanceof AbstractAetherSmeltingRecipe;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (this.canEqual(obj) && ((AbstractAetherSmeltingRecipe<?>) obj).canEqual(this)) {
			AbstractAetherSmeltingRecipe<?> ench = (AbstractAetherSmeltingRecipe<?>) obj;
			
			return ItemStack.areItemsEqualIgnoreDurability(this.getInput(), ench.getInput())
					&& ItemStack.areItemsEqualIgnoreDurability(this.getOutput(), ench.getOutput());
		} else {
			return false;
		}
	}
	
}
