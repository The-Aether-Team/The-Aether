package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.component.AetherMenuButton;
import com.aetherteam.aether.client.gui.component.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.internal.BrandingControl;

import javax.annotation.Nonnull;

public class AetherTitleScreen extends TitleScreen {
	public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU.getHolder().orElseThrow(), 20, 600, true);

	private final PanoramaRenderer panorama = new PanoramaRenderer(new CubeMap(new ResourceLocation(Aether.MODID, "textures/gui/title/panorama/panorama")));
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation(Aether.MODID, "textures/gui/title/aether.png");

	private AetherTitleScreenModUpdateIndicator modUpdateNotification;

	private boolean alignedLeft;

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
		this.alignedLeft = this.alignElementsLeft();
		this.setupButtons();
		this.modUpdateNotification = new AetherTitleScreenModUpdateIndicator();
		this.modUpdateNotification.init();
	}

	public void setupButtons() {
		int buttonCount = 0;
		for (Renderable renderable : this.renderables) {
			if (renderable instanceof AetherMenuButton aetherMenuButton) {
				if (this.alignElementsLeft()) {
					aetherMenuButton.setX(30);
					aetherMenuButton.setY(80 + buttonCount * 25);
					aetherMenuButton.setWidth(200);
				} else {
					aetherMenuButton.setX(aetherMenuButton.initialX);
					aetherMenuButton.setY(aetherMenuButton.initialY - 10);
					aetherMenuButton.setWidth(aetherMenuButton.initialWidth);
				}
				buttonCount++;
			}
		}
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
		if (this.alignedLeft != this.alignElementsLeft()) {
			this.alignedLeft = this.alignElementsLeft();
			this.setupButtons();
		}
		if (this.minecraft != null) {
			if (titleScreenAccessor.aether$getFadeInStart() == 0L && titleScreenAccessor.aether$isFading()) {
				titleScreenAccessor.aether$setFadeInStart(Util.getMillis());
			}
			float f = titleScreenAccessor.aether$isFading() ? (float) (Util.getMillis() - titleScreenAccessor.aether$getFadeInStart()) / 1000.0F : 1.0F;
			this.panorama.render(partialTicks, Mth.clamp(f, 0.0F, 1.0F));
			RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
			RenderSystem.enableBlend();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, titleScreenAccessor.aether$isFading() ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
			blit(poseStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			float f1 = titleScreenAccessor.aether$isFading() ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
			this.setupLogo(poseStack,  f1);
			int l = Mth.ceil(f1 * 255.0F) << 24;
			if ((l & -67108864) != 0) {
				ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, l);
				if (titleScreenAccessor.aether$getSplash() != null) {
					float splashX = this.alignElementsLeft() ? 200.0F : (float) this.width / 2 + 90;
					float splashY = this.alignElementsLeft() ? 50.0F : 70.0F;
					poseStack.pushPose();
					poseStack.translate(splashX, splashY, 0.0F);
					poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
					float f2 = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
					f2 = f2 * 100.0F / (float) (this.font.width(titleScreenAccessor.aether$getSplash()) + 32); poseStack.scale(f2, f2, f2);
					drawCenteredString(poseStack, this.font, titleScreenAccessor.aether$getSplash(), 0, -8, 16776960 | l);
					poseStack.popPose();
				}

				if (this.alignElementsLeft()) {
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
						abstractWidget.setAlpha(f1);
						abstractWidget.visible = true;
					} else {
						abstractWidget.visible = false;
					}
				}
			}

			int offset = 0;
			for (Renderable renderable : this.renderables) {
				renderable.render(poseStack, mouseX, mouseY, partialTicks);
				if (renderable instanceof AetherMenuButton aetherButton) {
					if (aetherButton.isMouseOver(mouseX, mouseY)) {
						if (aetherButton.renderOffset < 15) {
							aetherButton.renderOffset += 4;
						}
					} else {
						if (aetherButton.renderOffset > 0) {
							aetherButton.renderOffset -= 4;
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

	private void setupLogo(PoseStack poseStack, float transparency) {
		RenderSystem.setShaderTexture(0, AETHER_LOGO);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency);
		int logoX = this.alignElementsLeft() ? 10 : this.width / 2 - 102;
		int logoY = this.alignElementsLeft() ? 15 : 30;
		this.blit(poseStack, logoX, logoY, 0, 0, 155, 44);
		this.blit(poseStack, logoX + 155, logoY, 0, 45, 155, 44);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(@Nonnull T renderable) {
		if (renderable instanceof Button button) {
			if (this.isButtonAether(button.getMessage())) {
				AetherMenuButton aetherButton = new AetherMenuButton(button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}
		return super.addRenderableWidget(renderable);
	}

	public boolean isButtonAether(Component buttonText) {
		return buttonText.equals(Component.translatable("menu.singleplayer"))
				|| buttonText.equals(Component.translatable("menu.multiplayer"))
				|| buttonText.equals(Component.translatable("menu.online"))
				|| buttonText.equals(Component.translatable("fml.menu.mods"))
				|| buttonText.equals(Component.translatable("menu.options"))
				|| buttonText.equals(Component.translatable("menu.quit"));
	}

	public boolean alignElementsLeft() { //todo remove, split into two new menus
		return (AetherConfig.CLIENT.menu_type_toggles_alignment.get() && AetherConfig.CLIENT.enable_world_preview.get()) || AetherConfig.CLIENT.align_aether_menu_elements_left.get();
	}
}
