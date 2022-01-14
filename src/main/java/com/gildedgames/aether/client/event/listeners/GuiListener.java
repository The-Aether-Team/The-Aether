package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.button.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;

import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.common.event.listeners.DimensionListener;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.OpenAccessoriesPacket;
import com.gildedgames.aether.core.util.TriviaReader;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GuiListener
{
	private static boolean shouldAddButton = true;

	@SubscribeEvent
	public static void onInput(TickEvent.ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		Player entity = minecraft.player;
		if (event.phase == TickEvent.Phase.END) {
			if (entity != null) {
				if (AetherKeys.openAccessoryInventory.consumeClick() && minecraft.isWindowActive()) {
					AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(entity.getId(), ItemStack.EMPTY));
					shouldAddButton = false;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onGuiOpen(ScreenOpenEvent event) {
		if (event.getScreen() instanceof TitleScreen) {
			if (AetherConfig.CLIENT.enable_aether_menu.get()) {
				event.setScreen(new AetherMainMenuScreen());
			}
		}
	}

	@SubscribeEvent
	public static void onGuiInitialize(ScreenEvent.InitScreenEvent.Post event) {
		Screen screen = event.getScreen();
		if (screen instanceof TitleScreen) {
			if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
				Button themeSwitchButton = new Button(screen.width - 24, 4, 20, 20, new TextComponent("T"),
						(pressed) -> {
							AetherConfig.CLIENT.enable_aether_menu.set(!AetherConfig.CLIENT.enable_aether_menu.get());
							AetherConfig.CLIENT.enable_aether_menu.save();
							Minecraft.getInstance().setScreen(AetherConfig.CLIENT.enable_aether_menu.get() ? new AetherMainMenuScreen() : new TitleScreen());
						},
						(button, matrixStack, x, y) ->
								screen.renderTooltip(matrixStack, new TranslatableComponent(AetherConfig.CLIENT.enable_aether_menu.get() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether"), x + 4, y + 12));

				event.addListener(themeSwitchButton);
			}
		}
		Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(screen);
		if (screen instanceof InventoryScreen || screen instanceof CuriosScreen || screen instanceof CreativeModeInventoryScreen) {
			AbstractContainerScreen<?> inventoryScreen = (AbstractContainerScreen<?>) screen;
			event.addListener(new AccessoryButton(inventoryScreen, inventoryScreen.getGuiLeft() + offsets.getA(), inventoryScreen.getGuiTop() + offsets.getB(), AccessoriesScreen.ACCESSORIES_BUTTON));
		}
		if (screen instanceof AccessoriesScreen accessoriesScreen) {
			if (shouldAddButton) {
				event.addListener(new AccessoryButton(accessoriesScreen, accessoriesScreen.getGuiLeft() + offsets.getA(), accessoriesScreen.getGuiTop() + offsets.getB(), AccessoriesScreen.ACCESSORIES_BUTTON));
			} else {
				shouldAddButton = true;
			}
		}
	}

	@SubscribeEvent
	public static void onGuiDraw(ScreenEvent.DrawScreenEvent event) {
		Screen screen = event.getScreen();
		PoseStack matrixStack = event.getPoseStack();
		if (screen instanceof GenericDirtMessageScreen) {
			Component triviaLine = TriviaReader.getTriviaLine();
			if (triviaLine != null && !screen.getTitle().equals(new TranslatableComponent("menu.savingLevel")) && AetherConfig.CLIENT.enable_trivia.get()) {
				Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, triviaLine, screen.width / 2, screen.height - 16, 16777113);
			}
		}
		if (screen instanceof ReceivingLevelScreen) {
			if (Minecraft.getInstance().player != null) {
				ResourceKey<Level> dimension = Minecraft.getInstance().player.level.dimension();
				if (dimension == AetherDimensions.AETHER_WORLD) {
					Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
				} else {
					if (DimensionListener.leavingAether) {
						Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
					}
				}
			}
		}
		if (screen instanceof ProgressScreen) {
			if (Minecraft.getInstance().player != null) {
				ResourceKey<Level> dimension = Minecraft.getInstance().player.level.dimension();
				if (dimension == AetherDimensions.AETHER_WORLD) {
					Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
				} else {
					IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> {
						if (aetherPlayer.isInPortal()) {
							Screen.drawCenteredString(matrixStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
						}
					});
				}
			}
		}
	}
}
