package com.gildedgames.aether.client.gui.button;

import com.gildedgames.aether.Aether;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button
{
	public static final ResourceLocation AETHER_WIDGETS = new ResourceLocation(Aether.MODID, "textures/gui/title/buttons.png");
	public int initialX;
	public int renderOffset;
	
	public AetherMenuButton(int xPos, int yPos, int width, int height, Component message, OnPress callback) {
		super(xPos, yPos, width, height, message, callback);
		this.initialX = xPos;
	}

	public AetherMenuButton(Button oldButton) {
		this(oldButton.x, oldButton.y, oldButton.getWidth(), oldButton.getHeight(), oldButton.getMessage(), (button) -> oldButton.onPress());
		oldButton.visible = false;
		oldButton.active = false;
		this.initialX = oldButton.x;
		this.renderOffset = 0;
	}

	@Override
	public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float p_230431_4_) {
		Minecraft minecraft = Minecraft.getInstance();
	    Font fontrenderer = minecraft.font;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, AETHER_WIDGETS);
	    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
	    int i = this.getYImage(this.isHovered);
	    RenderSystem.enableBlend();
	    RenderSystem.defaultBlendFunc();
	    RenderSystem.enableDepthTest();
	    this.blit(matrixStack, this.x + this.renderOffset, this.y, 0, 46 + i * 20, this.width / 2, this.height);
	    this.blit(matrixStack, (this.x + this.width / 2) + this.renderOffset, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
	    this.renderBg(matrixStack, minecraft, mouseX, mouseY);
	    drawString(matrixStack, fontrenderer, this.getMessage(), this.x + 35 + this.renderOffset, this.y + (this.height - 8) / 2, this.getColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
	}

	public int getColor(int mouseX, int mouseY) {
		return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
	}
}
