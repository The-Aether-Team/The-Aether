package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OpenGuiListener
{
	@SubscribeEvent
	public static void onGuiInitialize(GuiScreenEvent.InitGuiEvent.Post event) {
		if (event.getGui() instanceof MainMenuScreen) {
			if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
				Screen gui = event.getGui();
				Button themeSwitchButton = new Button(gui.width - 22, 2, 20, 20, new StringTextComponent("T"),
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
