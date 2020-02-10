package com.aether.api.accessories;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class AetherAccessory extends ForgeRegistryEntry<AetherAccessory> {
	private ItemStack accessoryStack;
	private AccessoryType accessoryType;
	
	public AetherAccessory(@Nonnull Block item, @Nonnull AccessoryType type) {
		this(new ItemStack(item), type);
	}
	
	public AetherAccessory(@Nonnull Item item, @Nonnull AccessoryType type) {
		this(new ItemStack(item), type);
	}
	
	public AetherAccessory(@Nonnull ItemStack stack, @Nonnull AccessoryType type) {
		Validate.notNull(stack, "Item stack was null");
		Validate.notNull(type, "Accessory type was null");
		
		this.accessoryType = type;
		this.accessoryStack = stack;
		
		this.setRegistryName(stack.getItem().getRegistryName());
	}
	
	public @Nonnull AccessoryType getAccessoryType() {
		return accessoryType;
	}
	
	public @Nonnull ItemStack getAccessoryStack() {
		return accessoryStack; // TODO copy the stack before returning it?
	}
	
}
