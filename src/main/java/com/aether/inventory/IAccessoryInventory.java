package com.aether.inventory;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;

public interface IAccessoryInventory extends IInventory {

	PlayerEntity getOwner();
	
	boolean isWearingAccessory(Item item);
	
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
	
}
