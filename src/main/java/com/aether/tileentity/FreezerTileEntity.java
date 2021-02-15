package com.aether.tileentity;

import com.aether.Aether;
import com.aether.api.AetherAPI;
import com.aether.inventory.container.FreezerContainer;
import com.aether.crafting.AetherRecipeTypes;

import com.aether.registry.AetherTileEntityTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FreezerTileEntity extends AbstractFurnaceTileEntity {

	protected FreezerTileEntity(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(tileTypeIn, recipeTypeIn);
	}
	
	public FreezerTileEntity() {
		super(AetherTileEntityTypes.FREEZER.get(), AetherRecipeTypes.FREEZING);
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + Aether.MODID + ".freezer");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new FreezerContainer(id, player, this, this.furnaceData);
	}
	
	@Override
	protected int getBurnTime(ItemStack stack) {
		return !stack.isEmpty()? AetherAPI.getFreezableFuelTime(stack.getItem()) : 0;
	}

}
