package com.aetherteam.aether.client.gui.component.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button {
	private static final ResourceLocation AETHER_WIDGETS = new ResourceLocation(Aether.MODID, "textures/gui/title/buttons.png");
	public static final int BUTTON_WIDTH = 400;
	public static final int BUTTON_HEIGHT = 40;
	public static final int BUTTON_SEPARATION = 50;
	public static final int INITIAL_X_OFFSET = 16;
	public static final int INITIAL_Y_OFFSET = 100;
	public static final int TEXTURE_SIZE = 512;
	private final AetherTitleScreen menu;
	public int originalX;
	public int originalY;
	public int originalWidth;
	public int originalHeight;
	public int hoverOffset;
	public int buttonCountOffset;
	
	public AetherMenuButton(AetherTitleScreen menu, int x, int y, int width, int height, Component message, OnPress callback, CreateNarration narration) {
		super(x, y, width, height, message, callback, narration);
		this.menu = menu;
		this.originalX = x;
		this.originalY = y;
		this.originalWidth = width;
		this.originalHeight = height;
		this.hoverOffset = 0;
	}

	public AetherMenuButton(AetherTitleScreen menu, Button oldButton) {
		this(menu, oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight(), oldButton.getMessage(), (button) -> oldButton.onPress(), (button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage());
		oldButton.visible = false;
		oldButton.active = false;
	}

	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		int i = this.getTextureY();

		float scale = AetherTitleScreen.getScale(this.menu, minecraft);
		if (this.menu.isAlignedLeft()) {
			this.setX(INITIAL_X_OFFSET);
			this.setY((int) ((INITIAL_Y_OFFSET / scale) + this.buttonCountOffset * (BUTTON_SEPARATION / scale)));
			this.setWidth((int) (BUTTON_WIDTH / scale));
		} else {
			this.setX(this.originalX);
			this.setY((int) (10 + (this.height / 2 + (96 / scale)) + (BUTTON_SEPARATION / scale) * this.buttonCountOffset));
			this.setWidth(this.originalWidth);
		}
		this.setHeight((int) (BUTTON_HEIGHT / scale));

		RenderSystem.setShaderTexture(0, AETHER_WIDGETS);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
	    blit(poseStack, this.getX() + this.hoverOffset, this.getY(), 0, Mth.ceil(i / scale), this.getWidth(), this.getHeight(), (int) (TEXTURE_SIZE / scale), (int) (TEXTURE_SIZE / scale));
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();

		poseStack.pushPose();
		float textScale = getScale(this.menu, minecraft);
		float textX = this.getX() + (35 * textScale) + this.hoverOffset;
		float textY = this.getY() + (this.height - (8 * textScale)) / 2.0F;
		poseStack.translate(textX, textY, 0.0F);
		poseStack.scale(textScale, textScale, textScale);
	    drawString(poseStack, font, this.getMessage(), 0, 0, this.getColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
		poseStack.popPose();
	}

	public static float getScale(AetherTitleScreen titleScreen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());
		float elementScale = AetherTitleScreen.getScale(titleScreen, minecraft);
		float elementPixelWidth = (int) (guiScale / elementScale);
		float textPixelWidth = elementPixelWidth + 2.0F;
		if (elementPixelWidth <= 1) {
			textPixelWidth = 2.0F;
		}
		return textPixelWidth / guiScale;
	}

	/**
	 * [CODE COPY] - {@link AbstractButton#getTextureY()}
	 * Modified the final offset multipliers.
	 */
	private int getTextureY() {
		int i = 1;
		if (!this.isActive()) {
			i = 0;
		} else if (this.isHoveredOrFocused()) {
			i = 2;
		}
		return i * 40;
	}

	public int getColor(int mouseX, int mouseY) {
		return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
	}

	public static int totalHeightRange(int buttonCount, float scale) {
		return (int) ((INITIAL_Y_OFFSET / scale) + ((buttonCount) * (BUTTON_SEPARATION / scale)));
	}
}
