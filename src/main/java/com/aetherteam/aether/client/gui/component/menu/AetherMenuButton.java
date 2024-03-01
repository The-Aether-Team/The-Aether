package com.aetherteam.aether.client.gui.component.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button {
	private static final ResourceLocation AETHER_WIDGETS = new ResourceLocation(Aether.MODID, "textures/gui/title/buttons.png");
	private static final int BUTTON_WIDTH = 400;
	private static final int BUTTON_HEIGHT = 40;
	private static final int BUTTON_SEPARATION = 50;
	private static final int INITIAL_X_OFFSET = 16;
	private static final int INITIAL_Y_OFFSET = 100;
	private static final int TEXTURE_SIZE = 512;
	private final AetherTitleScreen screen;
	public final int originalX;
	public final int originalY;
	public final int originalWidth;
	public final int originalHeight;
	public int hoverOffset;
	public int buttonCountOffset;
	public boolean serverButton;

	public AetherMenuButton(AetherTitleScreen screen, Builder builder) {
		super(builder);
		this.screen = screen;
		this.originalX = this.getX();
		this.originalY = this.getY();
		this.originalWidth = this.getWidth();
		this.originalHeight = this.getHeight();
		this.hoverOffset = 0;
	}

	public AetherMenuButton(AetherTitleScreen screen, Button oldButton) {
		this(screen, new Builder(oldButton.getMessage(), (button) -> oldButton.onPress()).bounds( oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight()).createNarration((button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage()));
		oldButton.visible = false;
		oldButton.active = false;
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		PoseStack poseStack = guiGraphics.pose();
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		int i = this.getTextureY();

		float scale = AetherTitleScreen.getScale(this.screen, minecraft); // The scaling for elements relative to the true screen scale.
		if (this.screen.isAlignedLeft()) { // Changes button positioning dependent on whether the parent title screen is aligned left or not.
			this.setX(INITIAL_X_OFFSET);
			this.setY((int) ((INITIAL_Y_OFFSET / scale) + this.buttonCountOffset * (BUTTON_SEPARATION / scale)));
			this.setWidth((int) (BUTTON_WIDTH / scale));
		} else {
			this.setX(this.originalX);
			this.setY((int) (10 + (this.height / 2 + (96 / scale)) + (BUTTON_SEPARATION / scale) * this.buttonCountOffset));
			this.setWidth(this.originalWidth);
		}
		this.setHeight((int) (BUTTON_HEIGHT / scale));

		RenderSystem.enableBlend();
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
		guiGraphics.blit(AETHER_WIDGETS, this.getX() + this.hoverOffset, this.getY(), 0, Mth.ceil(i / scale), this.getWidth(), this.getHeight(), (int) (TEXTURE_SIZE / scale), (int) (TEXTURE_SIZE / scale));
		RenderSystem.disableBlend();

		poseStack.pushPose();
		float textScale = getTextScale(this.screen, minecraft);  // The scaling for text relative to the true screen scale.
		float textX = this.getX() + (35 * textScale) + this.hoverOffset;
		float textY = this.getY() + (this.height - (8 * textScale)) / 2.0F;
		poseStack.translate(textX, textY, 0.0F);
		poseStack.scale(textScale, textScale, textScale);
		guiGraphics.drawString(font, this.getMessage(), 0, 0, this.getTextColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
		poseStack.popPose();
	}

	/**
	 * Determines the proper text scaling relative to the proper button element scaling.
	 * @param screen The parent {@link AetherTitleScreen}.
	 * @param minecraft The {@link Minecraft} instance.
	 * @return The {@link Float} scale for the text.
	 */
	public static float getTextScale(AetherTitleScreen screen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode()); // The true screen GUI scale.
		float elementScale = AetherTitleScreen.getScale(screen, minecraft); // The scaling for elements relative to the true screen scale.
		float elementPixelWidth = (int) (guiScale / elementScale); // How many pixels-per-pixel for a rendered element.
		float textPixelWidth = elementPixelWidth + 2.0F; // How many pixels-per-pixel for text.
		if (elementPixelWidth <= 1) {
			textPixelWidth = 2.0F;
		}
		return textPixelWidth / guiScale; // Get the scaling for text relative to the amount of pixels-per-pixel that this text should have when rendering.
	}

	/**
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

	/**
	 * Determines the color for the button text depending on if its hovered over.
	 * @param mouseX The {@link Integer} for the mouse's x-position.
	 * @param mouseY The {@link Integer} for the mouse's y-position.
	 * @return The decimal {@link Integer} for the color.
	 */
	public int getTextColor(int mouseX, int mouseY) {
		if (!this.serverButton) {
			return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
		} else {
			return this.isMouseOver(mouseX, mouseY) ? 13746759 : 15457113;
		}
	}

	public static int totalHeightRange(int buttonCount, float scale) {
		return (int) ((INITIAL_Y_OFFSET / scale) + ((buttonCount) * ((BUTTON_SEPARATION + 10) / scale)));
	}
}
