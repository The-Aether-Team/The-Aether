package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.GuiHooks;
import com.gildedgames.aether.client.gui.button.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.client.gui.screens.Screen;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener {
	@SubscribeEvent
	public static void onGuiOpen(ScreenOpenEvent event) {
		Screen screen = event.getScreen();
		GuiHooks.drawSentryBackground(screen);
		AetherTitleScreen aetherMainMenuScreen = GuiHooks.openAetherMenu(screen);
		if (aetherMainMenuScreen != null) {
			event.setScreen(aetherMainMenuScreen);
		}
	}

	@SubscribeEvent
	public static void onInput(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			GuiHooks.openAccessoryMenu();
		}
	}

	@SubscribeEvent
	public static void onGuiInitialize(ScreenEvent.InitScreenEvent.Post event) {
		Screen screen = event.getScreen();

		Button toggleWorldButton = GuiHooks.setupToggleWorldButton(screen);
		if (toggleWorldButton != null) {
			event.addListener(toggleWorldButton);
		}

		Button menuSwitchButton = GuiHooks.setupMenuSwitchButton(screen);
		if (menuSwitchButton != null) {
			event.addListener(menuSwitchButton);
		}

        Button quickLoadButton = GuiHooks.setupQuickLoadButton(screen);
        if (quickLoadButton != null) {
            event.addListener(quickLoadButton);
        }

		Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(screen);
		AccessoryButton inventoryAccessoryButton = GuiHooks.setupAccessoryButtonWithinInventories(screen, offsets);
		if (inventoryAccessoryButton != null) {
			event.addListener(inventoryAccessoryButton);
		}

		AccessoryButton accessoryMenuAccessoryButton = GuiHooks.setupAccessoryButtonWithinAccessoryMenu(screen, offsets);
		if (accessoryMenuAccessoryButton != null) {
			event.addListener(accessoryMenuAccessoryButton);
		}
	}

	@SubscribeEvent
	public static void onGuiDraw(ScreenEvent.DrawScreenEvent event) {
		Screen screen = event.getScreen();
		PoseStack poseStack = event.getPoseStack();
		GuiHooks.drawTrivia(screen, poseStack);
		GuiHooks.drawAetherTravelMessage(screen, poseStack);
	}
}
