package com.aetherteam.aether.client.gui.component;

import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button
{
	public static final ResourceLocation AETHER_WIDGETS = new ResourceLocation("aether:textures/gui/title/buttons.png");
	public int initialX;
	public int initialY;
	public int initialWidth;
	public int renderOffset;
	
	public AetherMenuButton(int xPos, int yPos, int width, int height, Component message, OnPress callback, CreateNarration narration) {
		super(xPos, yPos, width, height, message, callback, narration);
		this.initialX = xPos;
		this.initialY = yPos;
		this.initialWidth = width;
	}

	public AetherMenuButton(Button oldButton) {
		this(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight(), oldButton.getMessage(), (button) -> oldButton.onPress(), (button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage());
		oldButton.visible = false;
		oldButton.active = false;
		this.renderOffset = 0;
	}

	@Override
	public void renderWidget(PoseStack matrixStack, int mouseX, int mouseY, float p_230431_4_) {
		Minecraft minecraft = Minecraft.getInstance();
	    Font fontrenderer = minecraft.font;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, AETHER_WIDGETS);
	    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		int i = this.getTextureY();
	    RenderSystem.enableBlend();
	    RenderSystem.defaultBlendFunc();
	    RenderSystem.enableDepthTest();
	    blit(matrixStack, this.getX() + this.renderOffset, this.getY(), 0, i, this.width / 2, this.height);
	    blit(matrixStack, (this.getX() + this.width / 2) + this.renderOffset, this.getY(), 200 - this.width / 2, i, this.width / 2, this.height);
	    drawString(matrixStack, fontrenderer, this.getMessage(), this.getX() + 35 + this.renderOffset, this.getY() + (this.height - 8) / 2, this.getColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
	}

	// [VANILLA COPY] - AbstractButton.getTextureY()
	private int getTextureY() {
		int i = 1;
		if (!this.isActive()) {
			i = 0;
		} else if (this.isHoveredOrFocused()) {
			i = 2;
		}

		return 46 + i * 20;
	}

	public int getColor(int mouseX, int mouseY) {
		return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
	}
}
