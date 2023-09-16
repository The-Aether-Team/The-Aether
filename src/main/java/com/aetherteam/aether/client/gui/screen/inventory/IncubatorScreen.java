package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.IncubatorRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IncubatorScreen extends AbstractRecipeBookScreen<IncubatorMenu, IncubatorRecipeBookComponent> {
	private static final ResourceLocation INCUBATOR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/incubator.png");

	public IncubatorScreen(IncubatorMenu menu, Inventory playerInventory, Component title) {
		super(menu, new IncubatorRecipeBookComponent(), playerInventory, title);
	}

	@Override
	public void init() {
		super.init();
		this.initScreen(37);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
		int left = this.getGuiLeft();
		int top = this.getGuiTop();
		guiGraphics.blit(INCUBATOR_GUI_TEXTURES, left, top, 0, 0, this.getXSize(), this.getYSize());
		if (this.getMenu().isIncubating()) {
			int incubationTimeRemaining = this.getMenu().getIncubationTimeRemaining();
			guiGraphics.blit(INCUBATOR_GUI_TEXTURES, left + 74, (top + 48) - incubationTimeRemaining, 176, 13 - incubationTimeRemaining, 14, incubationTimeRemaining + 1);
		}
		int incubationProgressScaled = this.getMenu().getIncubationProgressScaled();
		guiGraphics.blit(INCUBATOR_GUI_TEXTURES, left + 103, top + 70 - incubationProgressScaled, 179, 70 - incubationProgressScaled, 10, incubationProgressScaled);
	}
}
