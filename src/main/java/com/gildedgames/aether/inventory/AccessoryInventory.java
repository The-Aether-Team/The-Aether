package com.gildedgames.aether.inventory;

import static com.gildedgames.aether.api.accessories.AccessoryType.*;

import com.gildedgames.aether.api.accessories.AccessoryType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class AccessoryInventory extends Inventory implements IAccessoryInventory {

	private static final AccessoryType[] SLOT_TYPES = {PENDANT, CAPE, SHIELD, MISC, RING, RING, GLOVES, MISC};
	
	public final PlayerEntity player;
	
	public AccessoryInventory(PlayerEntity playerIn) {
		super(8);
		this.player = playerIn;
	}
	
	@Override
	public PlayerEntity getOwner() {
		return this.player;
	}
	
	@Override
	public boolean isWearingAccessory(Item item) {
		for (ItemStack stack : this.inventoryContents) {
			if (stack.getItem() == item) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void writeToNBT(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, this.inventoryContents);
	}

	@Override
	public void readFromNBT(CompoundNBT compound) {
		ItemStackHelper.loadAllItems(compound, this.inventoryContents);
	}

//	@Override
//	public void writeData(ByteBuf buf) {
//		
//	}

//	@Override
//	public void readData(ByteBuf buf) {
//		
//	}
	
	private boolean isWearingArmor(Item head, Item chest, Item legs, Item boots) {
		PlayerInventory inv = player.inventory;
		return (head == null || inv.armorItemInSlot(EquipmentSlotType.HEAD.getIndex()).getItem() == head)
			&& (chest == null || inv.armorItemInSlot(EquipmentSlotType.CHEST.getIndex()).getItem() == chest)
			&& (legs == null || inv.armorItemInSlot(EquipmentSlotType.LEGS.getIndex()).getItem() == legs)
			&& (boots == null || inv.armorItemInSlot(EquipmentSlotType.FEET.getIndex()).getItem() == boots);
	}

	@Override
	public boolean isWearingZaniteSet() {
		return false; // isWearingArmor(AetherItems.ZANITE_HELMET, AetherItems.ZANITE_CHESTPLATE, AetherItems.ZANITE_LEGGINGS, AetherItems.ZANITE_BOOTS);
	}

	@Override
	public boolean isWearingGravititeSet() {
		return false; // isWearingArmor(AetherItems.GRAVITITE_HELMET, AetherItems.GRAVITITE_CHESTPLATE, AetherItems.GRAVITITE_LEGGINGS, AetherItems.GRAVITITE_BOOTS);
	}

	@Override
	public boolean isWearingNeptuneSet() {
		return false; // isWearingArmor(AetherItems.NEPTUNE_HELMET, AetherItems.NEPTUNE_CHESTPLATE, AetherItems.NEPTUNE_LEGGINGS, AetherItems.NEPTUNE_BOOTS);
	}

	@Override
	public boolean isWearingPhoenixSet() {
		return false; // isWearingArmor(AetherItems.PHOENIX_HELMET, AetherItems.PHOENIX_CHESTPLATE, AetherItems.PHOENIX_LEGGINGS, AetherItems.PHOENIX_BOOTS);
	}

	@Override
	public boolean isWearingObsidianSet() {
		return false; // isWearingArmor(AetherItems.OBSIDIAN_HELMET, AetherItems.OBSIDIAN_CHESTPLATE, AetherItems.OBSIDIAN_LEGGINGS, AetherItems.OBSIDIAN_BOOTS);
	}

	@Override
	public boolean isWearingValkyrieSet() {
		return false; // isWearingArmor(AetherItems.VALKYRIE_HELMET, AetherItems.VALKYRIE_CHESTPLATE, AetherItems.VALKYRIE_LEGGINGS, AetherItems.VALKYRIE_BOOTS);
	}

}
