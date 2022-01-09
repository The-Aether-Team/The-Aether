package com.gildedgames.aether.common.inventory.container.slot;

import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IncubatorEggSlot extends Slot {
	private final IncubatorContainer container;
	private Player player;
	
	public IncubatorEggSlot(IncubatorContainer container, Container incubatorInventoryIn, int index, int xPosition, int yPosition, Player playerIn) {
		super(incubatorInventoryIn, index, xPosition, yPosition);
		this.container = container;
		this.player = playerIn;
	}
	
	@Override
	public int getMaxStackSize() {
		return 1;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return container.isEgg(stack);
	}
	
	@Override
	public void set(ItemStack stack) {
		super.set(stack);
		container.playerUUIDAcceptor.accept(player.getUUID());
	}

}
