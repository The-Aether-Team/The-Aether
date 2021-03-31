package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class IncubatorScreen extends ContainerScreen<IncubatorContainer> {
	private static final ResourceLocation INCUBATOR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/incubator.png");
	
	public IncubatorScreen(IncubatorContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, inventory, name);
	}

	@Override
	public void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(INCUBATOR_GUI_TEXTURES);
		int guiLeft = this.leftPos;
		int guiTop = this.topPos;
		this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
		if (this.menu.isIncubating()) {
			int freezingTimeRemaining = this.menu.getFreezingTimeRemaining();
			this.blit(matrixStack, guiLeft + 56, guiTop + 36 + 12 - freezingTimeRemaining, 176, 12 - freezingTimeRemaining, 14, freezingTimeRemaining + 2);
		}

		int progressionScaled = this.menu.getProgressionScaled();
		this.blit(matrixStack, guiLeft + 79, guiTop + 34, 176, 14, progressionScaled + 1, 16);
	}
}
