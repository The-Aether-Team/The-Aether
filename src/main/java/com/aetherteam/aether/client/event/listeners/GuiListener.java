package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.gui.component.AccessoryButton;
import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener {
	/** Set of UUIDs of boss bars that belong to Aether bosses. */
	public static final Set<UUID> BOSS_EVENTS = new HashSet<>();

	@SubscribeEvent
	public static void onGuiInitialize(ScreenEvent.Init.Post event) {
		Screen screen = event.getScreen();
		if (!AetherConfig.CLIENT.disable_accessory_button.get() && GuiHooks.areItemsPresent()) {
			Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(screen);
			AccessoryButton inventoryAccessoryButton = GuiHooks.setupAccessoryButtonWithinInventories(screen, offsets);
			if (inventoryAccessoryButton != null) {
				event.addListener(inventoryAccessoryButton);
			}

			AccessoryButton accessoryMenuAccessoryButton = GuiHooks.setupAccessoryButtonWithinAccessoryMenu(screen, offsets);
			if (accessoryMenuAccessoryButton != null) {
				event.addListener(accessoryMenuAccessoryButton);
			}
		} else {
			if (screen instanceof PauseScreen) {
				GridLayout layout = GuiHooks.setupPerksButtons(screen);
				layout.visitWidgets(event::addListener);
			}
		}
	}

	@SubscribeEvent
	public static void onGuiDraw(ScreenEvent.Render event) {
		Screen screen = event.getScreen();
		PoseStack poseStack = event.getPoseStack();
		GuiHooks.drawTrivia(screen, poseStack);
		GuiHooks.drawAetherTravelMessage(screen, poseStack);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (event.phase == TickEvent.Phase.END) {
			GuiHooks.tickMenuWhenPaused(minecraft);
			GuiHooks.handleRefreshRebound();
		}
	}

	@SubscribeEvent
	public static void onKeyPress(InputEvent.Key event) {
		GuiHooks.openAccessoryMenu();
		GuiHooks.closeContainerMenu(event.getKey(), event.getAction());
	}

	/**
	 * Draws the Aether boss bar.
	 */
	@SubscribeEvent
	public static void onRenderBoss(CustomizeGuiOverlayEvent.BossEventProgress event) {
		LerpingBossEvent bossEvent = event.getBossEvent();
		if (BOSS_EVENTS.contains(bossEvent.getId())) {
			GuiHooks.drawBossHealthBar(event.getPoseStack(), event.getX(), event.getY(), bossEvent);
			event.setIncrement(event.getIncrement() + 13);
			// This event is cancelled in BossHealthOverlayMixin. see it for more info.
			//event.setCanceled(true);
		}
	}
}
