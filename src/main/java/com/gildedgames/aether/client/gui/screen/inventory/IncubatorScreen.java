package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class IncubatorScreen extends AbstractContainerScreen<IncubatorContainer>
{
	private static final ResourceLocation INCUBATOR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/incubator.png");
	
	public IncubatorScreen(IncubatorContainer container, Inventory inventory, Component name) {
		super(container, inventory, name);
	}

	@Override
	public void init() {
		super.init();
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(poseStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@Nonnull PoseStack poseStack, float partialTicks, int x, int y) {
		RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, INCUBATOR_GUI_TEXTURES);
		int guiLeft = this.leftPos;
		int guiTop = this.topPos;
		this.blit(poseStack, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);

		if (this.menu.isIncubating()) {
			int incubationTimeRemaining = this.menu.getIncubationTimeRemaining();
			this.blit(poseStack, guiLeft + 74, (guiTop + 47) - incubationTimeRemaining, 176, 12 - incubationTimeRemaining, 14, incubationTimeRemaining + 2);
		}
		int progressionScaled = this.menu.getIncubationProgressScaled();
		this.blit(poseStack, guiLeft + 103, guiTop + 70 - progressionScaled, 179, 70 - progressionScaled, 10, progressionScaled);
	}
}
