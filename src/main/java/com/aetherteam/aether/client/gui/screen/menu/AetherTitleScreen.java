package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.component.menu.AetherMenuButton;
import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.internal.BrandingControl;

public class AetherTitleScreen extends TitleScreen {
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation(Aether.MODID, "textures/gui/title/aether.png");
	public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU.getHolder().orElseThrow(), 20, 600, true);
	private final PanoramaRenderer panorama = new PanoramaRenderer(new CubeMap(new ResourceLocation(Aether.MODID, "textures/gui/title/panorama/panorama")));
	private AetherTitleScreenModUpdateIndicator modUpdateNotification;
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
		this.modUpdateNotification = new AetherTitleScreenModUpdateIndicator(this);
		this.modUpdateNotification.init();
	}

	public void setupButtons() {
		int buttonRows = 0;
		int lastY = 0;
		for (Renderable renderable : this.renderables) {
			if (renderable instanceof AbstractWidget abstractWidget) {
				if (this.isImageButton(abstractWidget.getMessage())) {
					abstractWidget.visible = false;
				}
				if (abstractWidget instanceof AetherMenuButton aetherMenuButton) {
					if (this.isAlignedLeft()) {
						buttonRows++;
						aetherMenuButton.buttonCountOffset = buttonRows;
					} else {
						if (lastY < aetherMenuButton.originalY) {
							lastY = aetherMenuButton.originalY;
							buttonRows++;
						}
						aetherMenuButton.buttonCountOffset = buttonRows;
					}
				}
			}
		}
		this.rows = this.alignedLeft ? buttonRows : buttonRows - 1;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
		if (this.minecraft != null) {
			if (titleScreenAccessor.aether$getFadeInStart() == 0L && titleScreenAccessor.aether$isFading()) {
				titleScreenAccessor.aether$setFadeInStart(Util.getMillis());
			}
			float scale = getScale(this, this.minecraft);
			float f = titleScreenAccessor.aether$isFading() ? (float) (Util.getMillis() - titleScreenAccessor.aether$getFadeInStart()) / 1000.0F : 1.0F;
			this.panorama.render(partialTicks, Mth.clamp(f, 0.0F, 1.0F));
			RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
			RenderSystem.enableBlend();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, titleScreenAccessor.aether$isFading() ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
			blit(poseStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			float f1 = titleScreenAccessor.aether$isFading() ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
			this.setupLogo(poseStack, f1, scale);
			int l = Mth.ceil(f1 * 255.0F) << 24;
			if ((l & -67108864) != 0) {
				ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, l);
				if (titleScreenAccessor.aether$getSplash() != null) {
					float splashX = this.alignedLeft ? 400.0F / scale : (float) this.width / 2 + (175 / scale);
					float splashY = this.alignedLeft ? 100.0F / scale : (int) (20 + (76 / scale));
					poseStack.pushPose();
					poseStack.translate(splashX, splashY, 0.0F);
					poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
					float f2 = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
					String splash = titleScreenAccessor.aether$getSplash();
					f2 = f2 * (200.0F / scale) / (this.font.width(splash) + (64 / scale));
					poseStack.scale(f2, f2, f2);
					drawCenteredString(poseStack, this.font, splash, 0, (int) (-16 / scale), 16776960 | l);
					poseStack.popPose();
				}

				if (this.alignedLeft) {
					BrandingControl.forEachLine(true, true, (brdline, brd) ->
							drawString(poseStack, this.font, brd, width - this.font.width(brd) - 1, this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 16777215 | l)
					);
					BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
							drawString(poseStack, this.font, brd, 1, this.height - (brdline + 1) * (this.font.lineHeight + 1), 16777215 | l)
					);
				} else {
					BrandingControl.forEachLine(true, true, (brdline, brd) ->
							drawString(poseStack, this.font, brd, 2, this.height - (10 + brdline * (this.font.lineHeight + 1)), 16777215 | l)
					);
					BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
							drawString(poseStack, this.font, brd, this.width - this.font.width(brd), this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 16777215 | l)
					);
				}
			}

			for (GuiEventListener guiEventListener : this.children()) {
				if (guiEventListener instanceof AbstractWidget abstractWidget) {
					if (f1 > 0.02F) {
						if (!this.isImageButton(abstractWidget.getMessage())) {
							abstractWidget.setAlpha(f1);
							abstractWidget.visible = true;
						}
					} else {
						abstractWidget.visible = false;
					}
				}
			}

			int offset = CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
			for (Renderable renderable : this.renderables) {
				renderable.render(poseStack, mouseX, mouseY, partialTicks);
				if (renderable instanceof AetherMenuButton aetherButton) {
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
				if (renderable instanceof DynamicMenuButton dynamicMenuButton) {
					if (dynamicMenuButton.enabled) {
						offset -= 24;
					}
				}
			}
			for (Renderable renderable : this.renderables) {
				if (renderable instanceof Button button) {
					Component buttonText = button.getMessage();
					if (this.isImageButton(buttonText)) {
						button.visible = true;
					}
					if (buttonText.equals(Component.translatable("narrator.button.accessibility"))) {
						button.setX(this.width - 48 + offset);
						button.setY(4);
					} else if (buttonText.equals(Component.translatable("narrator.button.language"))) {
						button.setX(this.width - 24 + offset);
						button.setY(4);
					}
				}
			}

			if (f1 >= 1.0f) {
				this.modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
			}
		}
	}

	private void setupLogo(PoseStack poseStack, float transparency, float scale) {
		if (this.minecraft != null) {
			RenderSystem.setShaderTexture(0, AETHER_LOGO);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency);
			int width = (int) (350 / scale);
			int height = (int) (76 / scale);
			int logoX = this.alignedLeft ? (int) (10 + (18 / scale)) : (int) ((this.width / 2 - 175 / scale));
			int logoY = this.alignedLeft ? (int) (15 + (10 / scale)) : (int) (25 + (10 / scale));
			GuiComponent.blit(poseStack, logoX, logoY, 0, 0, width, height, width, height);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public static float getScale(AetherTitleScreen titleScreen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());
		return calculateScale(titleScreen, guiScale, guiScale - 1);
	}

	public static float calculateScale(AetherTitleScreen titleScreen, float guiScale, float lowerScale) {
		float scale = 1.0F;
		if (guiScale > 1) {
			scale = guiScale / lowerScale;
		}
		int range = AetherMenuButton.totalHeightRange(titleScreen.rows, scale);
		if (range > titleScreen.height && scale != 1.0F) {
            return calculateScale(titleScreen, guiScale, lowerScale - 1);
		} else {
			return scale;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T renderable) {
		if (renderable instanceof Button button) {
			if (this.isAetherButton(button.getMessage())) {
				AetherMenuButton aetherButton = new AetherMenuButton(this, button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}
		return super.addRenderableWidget(renderable);
	}

	public boolean isImageButton(Component buttonText) {
		return buttonText.equals(Component.translatable("narrator.button.accessibility"))
				|| buttonText.equals(Component.translatable("narrator.button.language"));
	}

	public boolean isAetherButton(Component buttonText) {
		return buttonText.equals(Component.translatable("menu.singleplayer"))
				|| buttonText.equals(Component.translatable("menu.multiplayer"))
				|| buttonText.equals(Component.translatable("menu.online"))
				|| buttonText.equals(Component.translatable("fml.menu.mods"))
				|| buttonText.equals(Component.translatable("menu.options"))
				|| buttonText.equals(Component.translatable("menu.quit"));
	}

	public boolean isAlignedLeft() {
		return this.alignedLeft;
	}
}
