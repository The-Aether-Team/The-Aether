package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.IncubatorRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
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
	protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
		RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, INCUBATOR_GUI_TEXTURES);
		int left = this.getGuiLeft();
		int top = this.getGuiTop();
		GuiComponent.blit(poseStack, left, top, 0, 0, this.getXSize(), this.getYSize());
		if (this.getMenu().isIncubating()) {
			int incubationTimeRemaining = this.getMenu().getIncubationTimeRemaining();
			GuiComponent.blit(poseStack, left + 74, (top + 48) - incubationTimeRemaining, 176, 13 - incubationTimeRemaining, 14, incubationTimeRemaining + 1);
		}
		int incubationProgressScaled = this.getMenu().getIncubationProgressScaled();
		GuiComponent.blit(poseStack, left + 103, top + 70 - incubationProgressScaled, 179, 70 - incubationProgressScaled, 10, incubationProgressScaled);
	}
}
