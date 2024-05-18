package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.gui.component.inventory.AccessoryButton;
import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import io.github.fabricators_of_create.porting_lib.event.client.KeyInputCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Tuple;
import net.minecraft.world.BossEvent;

import java.util.List;
import java.util.UUID;

public class GuiListener {
	/**
	 * @see AccessoriesScreen#getButtonOffset(Screen)
	 * @see GuiHooks#setupAccessoryButton(Screen, Tuple)
	 * @see GuiHooks#setupPerksButtons(Screen)
	 */
	public static void onGuiInitialize(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
		List<AbstractWidget> buttons = Screens.getButtons(screen);
		if (GuiHooks.isAccessoryButtonEnabled()) {
			Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(screen);
			AccessoryButton inventoryAccessoryButton = GuiHooks.setupAccessoryButton(screen, offsets);
			if (inventoryAccessoryButton != null) {
				buttons.add(inventoryAccessoryButton);
			}
		} else {
			GridLayout layout = GuiHooks.setupPerksButtons(screen);
			if (layout != null) {
				layout.visitWidgets(buttons::add);
			}
		}
	}

	/**
	 * @see GuiHooks#drawTrivia(Screen, GuiGraphics)
	 * @see GuiHooks#drawAetherTravelMessage(Screen, GuiGraphics)
	 */
	public static void onGuiDraw(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
		if (!FabricLoader.getInstance().isModLoaded("tipsmod")) {
			GuiHooks.drawTrivia(screen, guiGraphics);
		}
		GuiHooks.drawAetherTravelMessage(screen, guiGraphics);
	}

	/**
	 * @see GuiHooks#handlePatreonRefreshRebound()
	 */
	public static void onClientTick(Minecraft minecraft) {
		GuiHooks.handlePatreonRefreshRebound();
	}

	/**
	 * @see GuiHooks#openAccessoryMenu()
	 * @see GuiHooks#closeContainerMenu(int, int)
	 */
	public static void onKeyPress(int key, int scancode, int action, int mods) {
		GuiHooks.openAccessoryMenu();
		GuiHooks.closeContainerMenu(key, action);
	}

	/**
	 * This event is cancelled in BossHealthOverlayMixin. See it for more info.
	 * @see com.aetherteam.aether.mixin.mixins.client.BossHealthOverlayMixin#event(CustomizeGuiOverlayEvent.BossEventProgress)
	 * @see GuiHooks#drawBossHealthBar(GuiGraphics, int, int, LerpingBossEvent)
	 */
	public static int onRenderBossBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, int increment) {
		UUID bossUUID = bossEvent.getId();
		if (GuiHooks.isAetherBossBar(bossUUID)) {
			GuiHooks.drawBossHealthBar(guiGraphics, x, y, (LerpingBossEvent) bossEvent);
			return increment + 13;
		}

		return increment;
	}

	public static void init() {
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			ScreenEvents.afterRender(screen).register(GuiListener::onGuiDraw);
		});
		ScreenEvents.AFTER_INIT.register(GuiListener::onGuiInitialize);
		ClientTickEvents.END_CLIENT_TICK.register(GuiListener::onClientTick);
		KeyInputCallback.EVENT.register(GuiListener::onKeyPress);
	}
}
