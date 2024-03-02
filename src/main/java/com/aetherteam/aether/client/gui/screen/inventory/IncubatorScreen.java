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
	private static final ResourceLocation LIT_PROGRESS_TEXTURE = new ResourceLocation(Aether.MODID, "menu/lit_progress");
	private static final ResourceLocation INCUBATION_PROGRESS_TEXTURE = new ResourceLocation(Aether.MODID, "menu/incubation_progress");

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
			int incubationTimeRemaining = this.getMenu().getIncubationTimeRemaining() + 1;
			guiGraphics.blitSprite(LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - incubationTimeRemaining, left + 73, top + 36 + 13 - incubationTimeRemaining, 14, incubationTimeRemaining);
		}
		int incubationProgressScaled = this.getMenu().getIncubationProgressScaled() + 1;
		guiGraphics.blitSprite(INCUBATION_PROGRESS_TEXTURE, 10, 56, 0, 55 - incubationProgressScaled, left + 103, top + 15 + 55 - incubationProgressScaled, 10, incubationProgressScaled);
	}
}
