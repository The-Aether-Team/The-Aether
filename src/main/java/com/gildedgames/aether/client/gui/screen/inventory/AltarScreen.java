package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.AltarContainer;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class AltarScreen extends AetherFurnaceScreen<AltarContainer>
{
	private static final ResourceLocation ALTAR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/altar.png");
	
	public AltarScreen(AltarContainer container, Inventory inventory, Component name) {
		super(container, inventory, name, ALTAR_GUI_TEXTURES);
	}
}
