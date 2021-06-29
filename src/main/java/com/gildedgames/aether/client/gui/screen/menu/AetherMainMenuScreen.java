package com.gildedgames.aether.client.gui.screen.menu;

import com.gildedgames.aether.client.gui.button.AetherMenuButton;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.core.AetherConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherMainMenuScreen extends MainMenuScreen
{
	public static final BackgroundMusicSelector MENU = new BackgroundMusicSelector(AetherSoundEvents.MUSIC_MENU.get(), 20, 600, true);

	private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube(new ResourceLocation("aether:textures/gui/title/panorama/panorama")));
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
	protected <T extends Widget> T addButton(T widget) {
		if (widget instanceof Button) {
			Button button = (Button) widget;
			ITextComponent buttonText = button.getMessage();
			if (buttonText.equals(new TranslationTextComponent("menu.singleplayer"))
					|| buttonText.equals(new TranslationTextComponent("menu.multiplayer"))
					|| buttonText.equals(new TranslationTextComponent("fml.menu.mods"))
					|| buttonText.equals(new TranslationTextComponent("menu.online"))
					|| buttonText.equals(new TranslationTextComponent("menu.options"))
					|| buttonText.equals(new TranslationTextComponent("menu.quit"))) {
				button.x = 20;
				button.y = 70 + this.buttonCount * 25;
				button.setWidth(200);
				AetherMenuButton aetherButton = new AetherMenuButton(button);
				this.buttonCount++;
				return (T) super.addButton(aetherButton);
			} else if (buttonText.equals(new TranslationTextComponent("narrator.button.accessibility"))) {
				this.buttonAccessibility = button;
			} else if (buttonText.equals(new TranslationTextComponent("narrator.button.language"))) {
				this.buttonLanguage = button;
			}
			return (T) super.addButton(button);
		}
		return super.addButton(widget);
	}

	@Override
	protected void init() {
		super.init();
		this.buttonCount = 0;

		int buttonOffset = 0;
		if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
			buttonOffset = -22;
		}

		this.buttonLanguage.x = width - 22 + buttonOffset;
		this.buttonLanguage.y = 2;
		super.addButton(this.buttonLanguage);

		this.buttonAccessibility.x = width - 44 + buttonOffset;
		this.buttonAccessibility.y = 2;
		super.addButton(this.buttonAccessibility);

		this.modUpdateNotification = new AetherNotificationModUpdateScreen();
		this.modUpdateNotification.init();

		if (this.splash == null && this.minecraft != null) {
			this.splash = this.minecraft.getSplashManager().getSplash();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (this.minecraft != null) {
			fill(matrixStack, 0, 0, this.width, this.height, -1);
			this.panorama.render(partialTicks, 1.0F);
			this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);

			this.minecraft.getTextureManager().bind(AETHER_LOGO);
			this.blit(matrixStack, 10, 10, 0, 0, 155, 44);
			this.blit(matrixStack, 10 + 155, 10, 0, 45, 155, 44);

			net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, matrixStack, this.font, this.width, this.height, -1);

			if (this.splash != null) {
				RenderSystem.pushMatrix();
				RenderSystem.translatef((float) 200, 50.0F, 0.0F);
				RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				float f2 = 1.8F - MathHelper.abs(MathHelper.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
				f2 = f2 * 100.0F / (float) (this.font.width(this.splash) + 32); RenderSystem.scalef(f2, f2, f2);
				drawCenteredString(matrixStack, this.font, this.splash, 0, -8, 16776960 | 0xFF000000);
				RenderSystem.popMatrix();
			}

			net.minecraftforge.fml.BrandingControl.forEachLine(true, true, (brdline, brd) ->
					drawString(matrixStack, this.font, brd, width - this.font.width(brd) - 1, this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 0xFFFFFFFF)
			);

			net.minecraftforge.fml.BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
					drawString(matrixStack, this.font, brd, 1, this.height - (brdline + 1) * (this.font.lineHeight + 1), 0xFFFFFFFF)
			);

			for (Widget button : this.buttons) {
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
