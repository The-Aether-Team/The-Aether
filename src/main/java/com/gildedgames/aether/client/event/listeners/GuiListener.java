package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.screen.inventory.AetherFurnaceScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.io.File;
import java.util.List;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener
{
	public static boolean load_error = false;
	public static boolean load_level = false;
	@SubscribeEvent
	public static void onGuiInitialize(GuiScreenEvent.InitGuiEvent.Post event) {
		System.out.println(event.getGui());
		if (event.getGui() instanceof DisconnectedScreen)  {
			if (GuiListener.load_level == true) {
				System.out.println("load level!");
				GuiListener.load_level = false;
				Minecraft.getInstance().level = null;
				if (AetherConfig.CLIENT.enable_aether_menu.get()) {
					Minecraft.getInstance().setScreen(new AetherMainMenuScreen());
					Minecraft.getInstance().forceSetScreen(new AetherMainMenuScreen());
				} else {
					Minecraft.getInstance().setScreen(new MainMenuScreen());
					Minecraft.getInstance().forceSetScreen(new MainMenuScreen());
				}
			}
		}

		if (event.getGui() instanceof DirtMessageScreen ||
		event.getGui() instanceof WorkingScreen ||
		event.getGui() instanceof WinGameScreen ||
		event.getGui() instanceof MainMenuScreen &&
		!(event.getGui() instanceof AetherMainMenuScreen)) {
			if (GuiListener.load_level == true) {
				boolean flag = event.getGui() instanceof MainMenuScreen &&
						!(event.getGui() instanceof AetherMainMenuScreen);

				if (Minecraft.getInstance().level != null) {
					Minecraft.getInstance().options.hideGui = false;
					Minecraft.getInstance().level.disconnect();
					GuiListener.load_level = flag;
				}
			}
		}

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
	public static void onGuiOpen(GuiOpenEvent event) {

		if (event.getGui() instanceof MainMenuScreen) {

			if (AetherConfig.CLIENT.enable_aether_menu.get()) {
				event.setGui(new AetherMainMenuScreen());
			}
		}

	}
}
