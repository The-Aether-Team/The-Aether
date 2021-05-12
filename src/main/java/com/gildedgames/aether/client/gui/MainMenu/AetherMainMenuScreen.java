package com.gildedgames.aether.client.gui.MainMenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherMainMenuScreen extends MainMenuScreen {

	public enum Mode {
		AETHER, VANILLA
	}

	private Mode mode;
	private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube(new ResourceLocation("aether:textures/gui/title/panorama/panorama")));
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = new ResourceLocation("aether:textures/gui/title/aether.png");

	private Button buttonAccessibility;
	private Button buttonLanguage;
	private String splash;
	private AetherNotificationModUpdateScreen modUpdateNotification;

	public AetherMainMenuScreen(Mode mode) {
		this.mode = mode;

	}
	
	int btnCounter = 0;

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends Widget> T addButton(T button) {
		if (mode == Mode.VANILLA)
			return super.addButton(button);
		
		if (!(button instanceof Button))
			return null;

		// Handle accessibility and language separately
		if (button.getMessage().equals(new TranslationTextComponent("narrator.button.accessibility"))) {
			buttonAccessibility = (Button) button;
			return null;
		}
		if (button.getMessage().equals(new TranslationTextComponent("narrator.button.language"))) {
			buttonLanguage = (Button) button;
			return null;
		}
		
		// Reposition the Buttons
		button.x = 20;
		button.y = 70 + btnCounter * 25;
		button.setWidth(200);
		btnCounter++;

		// Wrap the buttons to AetherButtons
		AetherMainMenuButton newButton = new AetherMainMenuButton((Button) button);
		return (T) super.addButton(newButton);
	}

	@Override
	protected void init() {
		btnCounter = 0;
		super.init();
		
		//TODO Read value from config
		boolean themeSwitchButtonEnabled = true;

		if(themeSwitchButtonEnabled) {
			Button themeSwitchButton = new Button(width - 22, 2, 20, 20, new StringTextComponent("T"), (pressed) -> {
				minecraft.setScreen(new AetherMainMenuScreen(mode == Mode.VANILLA ? Mode.AETHER : Mode.VANILLA));
			}, (button, mstack, x, y)-> {
				renderTooltip(mstack, new StringTextComponent(mode == Mode.VANILLA ? "Aether Theme" : "Normal Theme"), x, y);
			});
			super.addButton(themeSwitchButton);
		}

		if (mode == Mode.AETHER) {
			
			int buttonOffset = 0;
			if(themeSwitchButtonEnabled)
				buttonOffset = -22;
				
			buttonLanguage.x = width - 22 + buttonOffset;
			buttonLanguage.y = 2;
			super.addButton(buttonLanguage);
			buttonAccessibility.x = width - 44 + buttonOffset;
			buttonAccessibility.y = 2;
			super.addButton(buttonAccessibility);
			modUpdateNotification = new AetherNotificationModUpdateScreen();
			modUpdateNotification.init();
			
			if (splash == null) {
				splash = this.minecraft.getSplashManager().getSplash();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

		if (mode == Mode.VANILLA) {
			super.render(matrixStack, mouseX, mouseY, partialTicks);
			return;
		}

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
		
		if (splash != null) { RenderSystem.pushMatrix();
		RenderSystem.translatef((float)(200), 50.0F, 0.0F);
		RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F); float f2 = 1.8F -
		MathHelper.abs(MathHelper.sin((float)(Util.getMillis() % 1000L) / 1000.0F *
		((float)Math.PI * 2F)) * 0.1F); f2 = f2 * 100.0F /
		(float)(this.font.width(splash) + 32); RenderSystem.scalef(f2, f2, f2);
		drawCenteredString(matrixStack, this.font, this.splash, 0, -8, 16776960 | 0xFF000000);
		RenderSystem.popMatrix(); }
		
		String s = "Minecraft " + SharedConstants.getCurrentVersion().getName();
		if (this.minecraft.isDemo()) {
			s = s + " Demo";
		} else {
			s = s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? ""
					: "/" + this.minecraft.getVersionType());
		}

		if (this.minecraft.isProbablyModded()) {
			s = s + I18n.get("menu.modded");
		}

		net.minecraftforge.fml.BrandingControl.forEachLine(true, true,
				(brdline, brd) -> drawString(matrixStack, this.font, brd, width - font.width(brd) - 1,
						this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 0xFFFFFFFF));

		net.minecraftforge.fml.BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
			drawString(matrixStack, this.font, brd, 1, this.height - (brdline + 1) * ( this.font.lineHeight + 1), 0xFFFFFFFF)
		);

		for (Widget button : buttons) {
			button.render(matrixStack, mouseX, mouseY, partialTicks);
			if (button instanceof AetherMainMenuButton) {
				AetherMainMenuButton aetherButton = (AetherMainMenuButton) button;
				if (aetherButton.isMouseOver(mouseX, mouseY)) {
					if (aetherButton.x < aetherButton.initialX + 15)
						aetherButton.x += 4;
				} else {
					if (aetherButton.x > aetherButton.initialX)
						aetherButton.x -= 4;
				}
			}
		}

		int copyrightX = width - font.width("Copyright Mojang AB. Do not distribute!") - 1;
		drawString(matrixStack, this.font, "Copyright Mojang AB. Do not distribute!", copyrightX, this.height - 10, 0xFFFFFFFF);
		if (mouseX > copyrightX && mouseX < width && mouseY > height - 10 && mouseY < this.height) {
			fill(matrixStack, copyrightX, this.height - 1,
					copyrightX + font.width("Copyright Mojang AB. Do not distribute!"), this.height, 0xFFFFFFFF);
		}
		 
		 modUpdateNotification.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
