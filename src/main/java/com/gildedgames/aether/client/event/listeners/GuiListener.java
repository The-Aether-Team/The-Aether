package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;

import com.gildedgames.aether.common.event.listeners.DimensionListener;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.util.TriviaReader;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener
{
	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent event) {
		if (event.getGui() instanceof MainMenuScreen) {
			if (AetherConfig.CLIENT.enable_aether_menu.get()) {
				event.setGui(new AetherMainMenuScreen());
			}
		}
	}

	@SubscribeEvent
	public static void onGuiInitialize(GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.getGui() instanceof MainMenuScreen) {
			if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
				Screen gui = event.getGui();
				Button themeSwitchButton = new Button(gui.width - 24, 4, 20, 20, new StringTextComponent("T"),
						(pressed) -> {
							AetherConfig.CLIENT.enable_aether_menu.set(!AetherConfig.CLIENT.enable_aether_menu.get());
							AetherConfig.CLIENT.enable_aether_menu.save();
							Minecraft.getInstance().setScreen(AetherConfig.CLIENT.enable_aether_menu.get() ? new AetherMainMenuScreen() : new MainMenuScreen());
						},
						(button, matrixStack, x, y) ->
								gui.renderTooltip(matrixStack, new TranslationTextComponent(AetherConfig.CLIENT.enable_aether_menu.get() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether"), x + 4, y + 12));

				event.addWidget(themeSwitchButton);
			}
		}
	}

	@SubscribeEvent
	public static void onGuiDraw(GuiScreenEvent.DrawScreenEvent event) {
		Screen screen = event.getGui();
		MatrixStack matrixStack = event.getMatrixStack();
		if (screen instanceof DirtMessageScreen) {
			ITextComponent triviaLine = TriviaReader.getTriviaLine();
			if (triviaLine != null && !screen.getTitle().equals(new TranslationTextComponent("menu.savingLevel")) && AetherConfig.CLIENT.enable_trivia.get()) {
				Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, triviaLine, screen.width / 2, screen.height - 16, 16777113);
			}
		}
		if (screen instanceof DownloadTerrainScreen) {
			if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
				RegistryKey<World> dimension = Minecraft.getInstance().player.level.dimension();
				if (dimension == AetherDimensions.AETHER_WORLD) {
					Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
				} else {
					if (DimensionListener.leavingAether) {
						Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
					}
				}
			}
		}
		if (screen instanceof WorkingScreen) {
			if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
				RegistryKey<World> dimension = Minecraft.getInstance().player.level.dimension();
				if (dimension == AetherDimensions.AETHER_WORLD) {
					Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
				} else {
					IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> {
						if (aetherPlayer.isInPortal()) {
							Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslationTextComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
						}
					});
				}
			}
		}
	}
}
