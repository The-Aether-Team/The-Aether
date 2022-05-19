package com.gildedgames.aether.client.gui.screen.menu;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.button.AetherMenuButton;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.core.AetherConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.math.Vector3f;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.sounds.Music;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.internal.BrandingControl;

import javax.annotation.Nonnull;

public class AetherTitleScreen extends TitleScreen {
	public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU.get(), 20, 600, true);

	private final PanoramaRenderer panorama = new PanoramaRenderer(new CubeMap(new ResourceLocation(Aether.MODID, "textures/gui/title/panorama/panorama")));
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation(Aether.MODID, "textures/gui/title/aether.png");

	private AetherNotificationModUpdateScreen modUpdateNotification;
	private String splash;

	public AetherTitleScreen() { }

	@Override
	protected void init() {
		super.init();
		int buttonCount = 0;
		int buttonOffset = 0;
		if (AetherConfig.CLIENT.enable_aether_menu_button.get()) { //todo: second world button will increase offset further.
			buttonOffset = -24;
		}
		for (Widget widget : this.renderables) {
			if (widget instanceof Button button) {
				Component buttonText = button.getMessage();
				if (this.isButtonAether(buttonText)) {
					button.x = 30;
					button.y = 80 + buttonCount * 25;
					button.setWidth(200);
					buttonCount++;
				} else if (buttonText.equals(new TranslatableComponent("narrator.button.accessibility"))) {
					button.x = width - 48 + buttonOffset;
					button.y = 4;
				} else if (buttonText.equals(new TranslatableComponent("narrator.button.language"))) {
					button.x = width - 24 + buttonOffset;
					button.y = 4;
				}
			}
		}
		this.modUpdateNotification = new AetherNotificationModUpdateScreen();
		this.modUpdateNotification.init();
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (this.minecraft != null) {
			fill(poseStack, 0, 0, this.width, this.height, -1);
			this.panorama.render(partialTicks, 1.0F);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			blit(poseStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, AETHER_LOGO);
			this.blit(poseStack, 10, 15, 0, 0, 155, 44);
			this.blit(poseStack, 10 + 155, 15, 0, 45, 155, 44);

			net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, -1);

			if (this.splash != null) {
				poseStack.pushPose();
				poseStack.translate((float) 200, 50.0F, 0.0F);
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
				float f2 = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
				f2 = f2 * 100.0F / (float) (this.font.width(this.splash) + 32); poseStack.scale(f2, f2, f2);
				drawCenteredString(poseStack, this.font, this.splash, 0, -8, 16776960 | 0xFF000000);
				poseStack.popPose();
			}

			BrandingControl.forEachLine(true, true, (brdline, brd) ->
					drawString(poseStack, this.font, brd, width - this.font.width(brd) - 1, this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 0xFFFFFFFF)
			);

			BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
					drawString(poseStack, this.font, brd, 1, this.height - (brdline + 1) * (this.font.lineHeight + 1), 0xFFFFFFFF)
			);

			for (Widget button : this.renderables) {
				button.render(poseStack, mouseX, mouseY, partialTicks);
				if (button instanceof AetherMenuButton aetherButton) {
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
			}

			this.modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
		}
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(@Nonnull T widget) {
		if (widget instanceof Button button) {
			if (this.isButtonAether(button.getMessage())) {
				AetherMenuButton aetherButton = new AetherMenuButton(button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}
		return super.addRenderableWidget(widget);
	}

	public boolean isButtonAether(Component buttonText) {
		return buttonText.equals(new TranslatableComponent("menu.singleplayer"))
				|| buttonText.equals(new TranslatableComponent("menu.multiplayer"))
				|| buttonText.equals(new TranslatableComponent("menu.online"))
				|| buttonText.equals(new TranslatableComponent("fml.menu.mods"))
				|| buttonText.equals(new TranslatableComponent("menu.options"))
				|| buttonText.equals(new TranslatableComponent("menu.quit"));
	}

	public void setSplash(String splash) {
		this.splash = splash;
	}
}
