package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.AetherMusicManager;
import com.gildedgames.aether.client.event.hooks.GuiHooks;
import com.gildedgames.aether.client.gui.component.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;

import com.gildedgames.aether.client.gui.screen.menu.AetherWorldDisplayHelper;
import com.gildedgames.aether.client.gui.screen.menu.VanillaLeftTitleScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
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
	public static void onGuiOpen(ScreenEvent.Opening event) {
		Screen screen = event.getScreen();
		GuiHooks.drawSentryBackground(screen);
		GuiHooks.setupWorldPreview(screen);
		VanillaLeftTitleScreen vanillaLeftTitleScreen = GuiHooks.openLeftDefaultMenu(screen);
		if (vanillaLeftTitleScreen != null) {
			event.setNewScreen(vanillaLeftTitleScreen);
		}
		AetherTitleScreen aetherMainMenuScreen = GuiHooks.openAetherMenu(screen);
		if (aetherMainMenuScreen != null) {
			event.setNewScreen(aetherMainMenuScreen);
		}
		GenericDirtMessageScreen bufferScreen = GuiHooks.openBufferScreen(screen);
		if (bufferScreen != null) {
			event.setNewScreen(bufferScreen);
		}
		GuiHooks.setupSplash(screen);
	}

	@SubscribeEvent
	public static void onGuiInitialize(ScreenEvent.Init.Post event) {
		Screen screen = event.getScreen();
		if (screen instanceof TitleScreen titleScreen) {
			GuiHooks.setSplashText(titleScreen);

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
			GuiHooks.setMenuAlignment();
		} else {
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
	}

	@SubscribeEvent
	public static void onGuiDraw(ScreenEvent.Render event) {
		Screen screen = event.getScreen();
		PoseStack poseStack = event.getPoseStack();
		Minecraft minecraft = Minecraft.getInstance();
		GuiHooks.drawTrivia(screen, poseStack);
		GuiHooks.drawAetherTravelMessage(screen, poseStack);
		GuiHooks.changeMenuAlignment(screen, minecraft);
	}


	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (event.phase == TickEvent.Phase.END) {
			GuiHooks.openAccessoryMenu();
			GuiHooks.tickMenuWhenPaused(minecraft);
			AetherMusicManager.tick();
		}
	}

	/**
	 * Resets the music on respawn.
	 */
	@SubscribeEvent
	public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
		AetherMusicManager.stopMusic();
	}

	/**
	 * Stops other music from playing over Aether music.
	 */
	@SubscribeEvent
	public static void onPlaySound(PlaySoundEvent event) {
		SoundInstance sound = event.getOriginalSound();
		if (sound.getSource() == SoundSource.MUSIC) {
			if (sound.getLocation().equals(SoundEvents.MUSIC_MENU.getLocation())) {
				AetherMusicManager.handleMenuMusic();
			} else if (AetherWorldDisplayHelper.loadedLevel != null) {
				AetherMusicManager.handleWorldPreviewMusic();
			} else if (sound.getLocation().equals(SoundEvents.MUSIC_CREATIVE.getLocation())) {
				AetherMusicManager.handleCreativeMusic();
			}
			if (AetherMusicManager.isPlaying) {
				event.setSound(null);
			}
		}
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
			event.setCanceled(true);
		}
	}
}
