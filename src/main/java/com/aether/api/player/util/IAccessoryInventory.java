package com.aether.api.player.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public interface IAccessoryInventory {

	void dropAccessories();
	
	void damageWornStack(int damage, ItemStack stack);
	
	boolean setAccessorySlot(ItemStack stack);
	
	boolean wearingAccessory(ItemStack stack);
	
	boolean wearingArmor(ItemStack stack);
	
	void writeToNBT(CompoundNBT compound);
	
	void readFromNBT(CompoundNBT compound);
	
	void writeData(ByteBuf buf);
	
	void readData(ByteBuf buf);
	
	boolean isWearingZaniteSet();
	
	boolean isWearingGravititeSet();
	
	boolean isWearingNeptuneSet();
	
	boolean isWearingPhoenixSet();
	
	boolean isWearingObsidianSet();
	
	boolean isWearingValkyrieSet();
	
	NonNullList<ItemStack> getAccessories();
	
	int getAccessoryCount(ItemStack stack);
	
}
