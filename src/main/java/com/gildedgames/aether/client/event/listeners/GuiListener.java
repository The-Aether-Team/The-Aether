package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.GuiHooks;
import com.gildedgames.aether.client.gui.button.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.client.gui.screens.Screen;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener {
	/** Set of UUIDs of boss bars that belong to Aether bosses. */
	public static final Set<UUID> BOSS_EVENTS = new HashSet<>();

	@SubscribeEvent
	public static void onGuiOpen(ScreenOpenEvent event) {
		Screen screen = event.getScreen();
		AetherMainMenuScreen aetherMainMenuScreen = GuiHooks.openAetherMenu(screen);
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
		Button menuSwitchButton = GuiHooks.setupMenuSwitchButton(screen);
		if (menuSwitchButton != null) {
			event.addListener(menuSwitchButton);
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

	/**
	 * Draws the Aether boss bar.
	 */
	@SubscribeEvent
	public static void onRenderBoss(RenderGameOverlayEvent.BossInfo event) {
		LerpingBossEvent bossEvent = event.getBossEvent();
		if (BOSS_EVENTS.contains(bossEvent.getId())) {
			GuiHooks.drawBossHealthBar(event.getMatrixStack(), event.getX(), event.getY(), bossEvent);
			event.setIncrement(event.getIncrement() + 11);
			event.setCanceled(true);
		}
	}
}
