package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.FreezerContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FreezerScreen extends AetherFurnaceScreen<FreezerContainer>
{
	private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/freezer.png");
	
	public FreezerScreen(FreezerContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, inventory, name, FREEZER_GUI_TEXTURES);
	}
}
