package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.FreezerContainer;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class FreezerScreen extends AetherFurnaceScreen<FreezerContainer>
{
	private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/freezer.png");
	
	public FreezerScreen(FreezerContainer container, Inventory inventory, Component name) {
		super(container, inventory, name, FREEZER_GUI_TEXTURES);
	}
}
