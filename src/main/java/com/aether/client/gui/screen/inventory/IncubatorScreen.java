package com.aether.client.gui.screen.inventory;

import com.aether.Aether;
import com.aether.inventory.container.IncubatorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IncubatorScreen extends ContainerScreen<IncubatorContainer> {
	private static final ResourceLocation INCUBATOR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/incubator.png");
	
	public IncubatorScreen(IncubatorContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, inventory, name);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(INCUBATOR_GUI_TEXTURES);
		int guiLeft = this.guiLeft;
		int guiTop = this.guiTop;
		this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		if (this.container.isIncubating()) {
			int freezingTimeRemaining = this.container.getFreezingTimeRemaining();
			this.blit(matrixStack, guiLeft + 56, guiTop + 36 + 12 - freezingTimeRemaining, 176, 12 - freezingTimeRemaining, 14, freezingTimeRemaining + 2);
		}

		int progressionScaled = this.container.getProgressionScaled();
		this.blit(matrixStack, guiLeft + 79, guiTop + 34, 176, 14, progressionScaled + 1, 16);
	}
}
