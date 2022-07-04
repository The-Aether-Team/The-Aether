package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.gildedgames.aether.inventory.menu.AltarMenu;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class AltarScreen extends AbstractFurnaceScreen<AltarMenu>
{
	private static final ResourceLocation ALTAR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");
	
	public AltarScreen(AltarMenu container, Inventory inventory, Component title) {
		super(container, new AltarRecipeBookComponent(), inventory, title, ALTAR_GUI_TEXTURES);
	}
}
