package com.gildedgames.aether.client.gui.screen.menu;

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
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.internal.BrandingControl;

public class AetherMainMenuScreen extends TitleScreen
{
	public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU.get(), 20, 600, true);

	private final PanoramaRenderer panorama = new PanoramaRenderer(new CubeMap(new ResourceLocation("aether:textures/gui/title/panorama/panorama")));
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation("aether:textures/gui/title/aether.png");

	private Button buttonAccessibility;
	private Button buttonLanguage;
	private String splash;
	private AetherNotificationModUpdateScreen modUpdateNotification;

	private int buttonCount;

	public AetherMainMenuScreen() {}

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends GuiEventListener & NarratableEntry> T addWidget(T widget) {
		if (widget instanceof Button) {
			Button button = (Button) widget;
			Component buttonText = button.getMessage();
			if (buttonText.equals(new TranslatableComponent("menu.singleplayer"))
					|| buttonText.equals(new TranslatableComponent("menu.multiplayer"))
					|| buttonText.equals(new TranslatableComponent("fml.menu.mods"))
					|| buttonText.equals(new TranslatableComponent("menu.online"))
					|| buttonText.equals(new TranslatableComponent("menu.options"))
					|| buttonText.equals(new TranslatableComponent("menu.quit"))) {
				button.x = 30;
				button.y = 80 + this.buttonCount * 25;
				button.setWidth(200);
				AetherMenuButton aetherButton = new AetherMenuButton(button);
				this.buttonCount++;
				return (T) super.addWidget(aetherButton);
			} else if (buttonText.equals(new TranslatableComponent("narrator.button.accessibility"))) {
				this.buttonAccessibility = button;
				return null;
			} else if (buttonText.equals(new TranslatableComponent("narrator.button.language"))) {
				this.buttonLanguage = button;
				return null;
			}
			return (T) super.addWidget(button);
		}
		return super.addWidget(widget);
	}

	@Override
	protected void init() {
		super.init();
		this.buttonCount = 0;

		int buttonOffset = 0;
		if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
			buttonOffset = -24;
		}

		this.buttonLanguage.x = width - 24 + buttonOffset;
		this.buttonLanguage.y = 4;
		super.addWidget(this.buttonLanguage);

		this.buttonAccessibility.x = width - 48 + buttonOffset;
		this.buttonAccessibility.y = 4;
		super.addWidget(this.buttonAccessibility);

		this.modUpdateNotification = new AetherNotificationModUpdateScreen();
		this.modUpdateNotification.init();

		if (this.splash == null && this.minecraft != null) {
			this.splash = this.minecraft.getSplashManager().getSplash();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (this.minecraft != null) {
			fill(matrixStack, 0, 0, this.width, this.height, -1);
			this.panorama.render(partialTicks, 1.0F);
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, AETHER_LOGO);
			this.blit(matrixStack, 10, 15, 0, 0, 155, 44);
			this.blit(matrixStack, 10 + 155, 15, 0, 45, 155, 44);

			net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, matrixStack, this.font, this.width, this.height, -1);

			if (this.splash != null) {
				matrixStack.pushPose();
				matrixStack.translate((float) 200, 50.0F, 0.0F);
				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
				float f2 = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
				f2 = f2 * 100.0F / (float) (this.font.width(this.splash) + 32); matrixStack.scale(f2, f2, f2);
				drawCenteredString(matrixStack, this.font, this.splash, 0, -8, 16776960 | 0xFF000000);
				matrixStack.popPose();
			}

			BrandingControl.forEachLine(true, true, (brdline, brd) ->
					drawString(matrixStack, this.font, brd, width - this.font.width(brd) - 1, this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 0xFFFFFFFF)
			);

			BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
					drawString(matrixStack, this.font, brd, 1, this.height - (brdline + 1) * (this.font.lineHeight + 1), 0xFFFFFFFF)
			);

			for (Widget button : this.renderables) {
				button.render(matrixStack, mouseX, mouseY, partialTicks);
				if (button instanceof AetherMenuButton) {
					AetherMenuButton aetherButton = (AetherMenuButton) button;
					if (aetherButton.isMouseOver(mouseX, mouseY)) {
						if (aetherButton.renderOffset < 15)
							aetherButton.renderOffset += 4;
					} else {
						if (aetherButton.renderOffset > 0)
							aetherButton.renderOffset -= 4;
					}
				}
			}

			int copyrightX = this.width - this.font.width("Copyright Mojang AB. Do not distribute!") - 1;
			drawString(matrixStack, this.font, "Copyright Mojang AB. Do not distribute!", copyrightX, this.height - 10, 0xFFFFFFFF);
			if (mouseX > copyrightX && mouseX < this.width && mouseY > this.height - 10 && mouseY < this.height) {
				fill(matrixStack, copyrightX, this.height - 1, copyrightX + this.font.width("Copyright Mojang AB. Do not distribute!"), this.height, 0xFFFFFFFF);
			}

			this.modUpdateNotification.render(matrixStack, mouseX, mouseY, partialTicks);
		}
	}
}
