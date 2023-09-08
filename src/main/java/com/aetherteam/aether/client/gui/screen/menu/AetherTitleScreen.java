package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.component.menu.AetherMenuButton;
import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.internal.BrandingControl;

public class AetherTitleScreen extends TitleScreen implements TitleScreenBehavior {
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation(Aether.MODID, "textures/gui/title/aether.png");
	public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU.get(), 20, 600, true);
	private final PanoramaRenderer panorama = new PanoramaRenderer(new CubeMap(new ResourceLocation(Aether.MODID, "textures/gui/title/panorama/panorama")));
	private AetherModUpdateIndicator modUpdateNotification;
	private boolean alignedLeft;
	private int rows;

	public AetherTitleScreen() {
		((TitleScreenAccessor) this).aether$setFading(true);
	}

	public AetherTitleScreen(boolean alignedLeft) {
		this();
		this.alignedLeft = alignedLeft;
	}

	@Override
	protected void init() {
		super.init();
		this.setupButtons();
		this.modUpdateNotification = new AetherModUpdateIndicator(this);
		this.modUpdateNotification.init();
	}

	public void setupButtons() {
		int buttonRows = 0;
		int lastY = 0;
		for (Widget renderable : this.renderables) {
			if (renderable instanceof AbstractWidget abstractWidget) {
				if (TitleScreenBehavior.isImageButton(abstractWidget.getMessage())) {
					abstractWidget.visible = false; // The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
				}
				if (abstractWidget instanceof AetherMenuButton aetherMenuButton) { // Sets button values that determine their positioning on the screen.
					if (this.isAlignedLeft()) {
						buttonRows++;
					} else {
						if (lastY < aetherMenuButton.originalY) {
							lastY = aetherMenuButton.originalY;
							buttonRows++;
						}
					}
					aetherMenuButton.buttonCountOffset = buttonRows;
				}
			}
		}
		this.rows = this.alignedLeft ? buttonRows : buttonRows - 1;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
		float fadeAmount = TitleScreenBehavior.super.handleFading(poseStack, this, titleScreenAccessor, this.panorama, PANORAMA_OVERLAY, partialTicks);
		float scale = getScale(this, this.getMinecraft());
		this.setupLogo(poseStack, fadeAmount, scale);
		int roundedFadeAmount = Mth.ceil(fadeAmount * 255.0F) << 24;
		if ((roundedFadeAmount & -67108864) != 0) {
			ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, roundedFadeAmount);
			if (titleScreenAccessor.aether$getSplash() != null) {
				float splashX = this.alignedLeft ? 400.0F / scale : (float) this.width / 2 + (175 / scale);
				float splashY = this.alignedLeft ? 100.0F / scale : (int) (20 + (76 / scale));
				poseStack.pushPose();
				poseStack.translate(splashX, splashY, 0.0F);
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
				float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
				String splash = titleScreenAccessor.aether$getSplash();
				textSize = textSize * (200.0F / scale) / (this.font.width(splash) + (64 / scale));
				poseStack.scale(textSize, textSize, textSize);
				GuiComponent.drawCenteredString(poseStack, this.font, splash, 0, (int) (-16 / scale), 16776960 | roundedFadeAmount);
				poseStack.popPose();
			}

			if (this.alignedLeft) {
				TitleScreenBehavior.super.renderRightBranding(poseStack, this, this.font, roundedFadeAmount);
			} else {
				BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
						drawString(poseStack, this.font, branding, 2, this.height - (10 + brandingLine * (this.font.lineHeight + 1)), 16777215 | roundedFadeAmount)
				);
				BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
						drawString(poseStack, this.font, branding, this.width - this.font.width(branding), this.height - (10 + (brandingLine + 1) * (this.font.lineHeight + 1)), 16777215 | roundedFadeAmount)
				);
			}
		}

		int xOffset = TitleScreenBehavior.super.handleButtonVisibility(this, fadeAmount);
		for (Widget renderable : this.renderables) {
			renderable.render(poseStack, mouseX, mouseY, partialTicks);
			if (renderable instanceof AetherMenuButton aetherButton) { // Smoothly shifts the Aether-styled buttons to the right slightly when hovered over.
				if (aetherButton.isMouseOver(mouseX, mouseY)) {
					if (aetherButton.hoverOffset < 15) {
						aetherButton.hoverOffset += 4;
					}
				} else {
					if (aetherButton.hoverOffset > 0) {
						aetherButton.hoverOffset -= 4;
					}
				}
			}
			if (renderable instanceof DynamicMenuButton dynamicMenuButton) {  // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
				if (dynamicMenuButton.enabled) {
					xOffset -= 24;
				}
			}
		}
		TitleScreenBehavior.super.handleImageButtons(this, xOffset);

		if (fadeAmount >= 1.0F) {
			this.modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
		}
	}

	/**
	 * Renders the Aether logo on the title screen.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param transparency The transparency {@link Float} for the logo.
	 * @param scale The {@link Float} for the scaling of the logo relative to the true screen scale.
	 */
	private void setupLogo(PoseStack poseStack, float transparency, float scale) {
		RenderSystem.setShaderTexture(0, AETHER_LOGO);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency);
		int width = (int) (350 / scale);
		int height = (int) (76 / scale);
		int logoX = this.alignedLeft ? (int) (10 + (18 / scale)) : (int) ((this.width / 2 - 175 / scale));
		int logoY = this.alignedLeft ? (int) (15 + (10 / scale)) : (int) (25 + (10 / scale));
		GuiComponent.blit(poseStack, logoX, logoY, 0, 0, width, height, width, height);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Determines the proper scaling for menu elements relative to the true screen scale.
	 * @param screen The parent {@link AetherTitleScreen}.
	 * @param minecraft The {@link Minecraft} instance.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float getScale(AetherTitleScreen screen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());  // The true screen GUI scale.
		return calculateScale(screen, guiScale, guiScale - 1);
	}
	/**
	 * Determines the proper scaling for menu elements relative to the given scale factors.
	 * @param screen The parent {@link AetherTitleScreen}.
	 * @param guiScale The base GUI scale {@link Float}.
	 * @param lowerScale A GUI scale {@link Float} value that is one less than the base.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float calculateScale(AetherTitleScreen screen, float guiScale, float lowerScale) {
		float scale = 1.0F;
		if (guiScale > 1) {
			scale = guiScale / lowerScale; // A scale factor to counteract the GUI scale option's changing of menu element's pixel scale (pixels-per-pixel).
		}
		int range = AetherMenuButton.totalHeightRange(screen.rows, scale);
		if (range > screen.height && scale != 1.0F) { // Recursive check to see if the menu elements can actually fit on the screen, otherwise it'll try to shrink to a lower GUI scale.
            return calculateScale(screen, guiScale, lowerScale - 1);
		} else {
			return scale;
		}
	}

	/**
	 * Changes main menu buttons into Aether-styled main menu buttons.<br><br>
	 * Warning for "unchecked" is suppressed because the buttons should always be able to be cast.
	 * @param renderable A renderable widget.
	 * @return A new renderable widget.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T renderable) {
		if (renderable instanceof Button button) {
			if (TitleScreenBehavior.isMainButton(button.getMessage())) {
				AetherMenuButton aetherButton = new AetherMenuButton(this, button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}
		return super.addRenderableWidget(renderable);
	}

	public boolean isAlignedLeft() {
		return this.alignedLeft;
	}
}
