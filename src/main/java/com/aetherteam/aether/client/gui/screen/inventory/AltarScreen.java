package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.AltarMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AltarScreen extends AbstractAetherFurnaceScreen<AltarMenu> {
	private static final ResourceLocation ALTAR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/altar.png");
	
	public AltarScreen(AltarMenu menu, Inventory inventory, Component title) {
		super(menu, new AltarRecipeBookComponent(), inventory, title, ALTAR_GUI_TEXTURES);
	}
}
