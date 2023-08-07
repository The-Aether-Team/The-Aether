package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.FreezerRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.FreezerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FreezerScreen extends AbstractAetherFurnaceScreen<FreezerMenu> {
	private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/freezer.png");
	
	public FreezerScreen(FreezerMenu menu, Inventory inventory, Component title) {
		super(menu, new FreezerRecipeBookComponent(), inventory, title, FREEZER_GUI_TEXTURES);
	}
}
