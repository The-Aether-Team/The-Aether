package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.client.gui.component.inventory.AccessoryButton;
//import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Tuple;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.UUID;

public class GuiListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(GuiListener::onGuiInitialize);
        bus.addListener(GuiListener::onGuiDraw);
        bus.addListener(GuiListener::onClientTick);
        bus.addListener(GuiListener::onKeyPress);
        bus.addListener(GuiListener::onRenderBossBar);
    }

    /**
     * @see AccessoriesScreen#getButtonOffset(Screen)
     * @see GuiHooks#setupAccessoryButton(Screen, Tuple)
     * @see GuiHooks#setupPerksButtons(Screen)
     */
    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
//        if (GuiHooks.isAccessoryButtonEnabled()) {
//            Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(screen);
//            AccessoryButton inventoryAccessoryButton = GuiHooks.setupAccessoryButton(screen, offsets);
//            if (inventoryAccessoryButton != null) {
//                event.addListener(inventoryAccessoryButton);
//            }
//        } else {
//            GridLayout layout = GuiHooks.setupPerksButtons(screen);
//            if (layout != null) {
//                layout.visitWidgets(event::addListener);
//            }
//        }
    }

    /**
     * @see GuiHooks#drawTrivia(Screen, GuiGraphics)
     * @see GuiHooks#drawAetherTravelMessage(Screen, GuiGraphics)
     */
    public static void onGuiDraw(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (!ModList.get().isLoaded("tipsmod")) {
            GuiHooks.drawTrivia(screen, guiGraphics);
        }
        GuiHooks.drawAetherTravelMessage(screen, guiGraphics);
    }

    /**
     * @see GuiHooks#handlePatreonRefreshRebound()
     */
    public static void onClientTick(ClientTickEvent event) {
        GuiHooks.handlePatreonRefreshRebound();
    }

    /**
     * @see GuiHooks#openAccessoryMenu()
     * @see GuiHooks#closeContainerMenu(int, int)
     */
    public static void onKeyPress(InputEvent.Key event) {
        GuiHooks.openAccessoryMenu();
        GuiHooks.closeContainerMenu(event.getKey(), event.getAction());
    }

    /**
     * This event is cancelled in BossHealthOverlayMixin. See it for more info.
     *
     * @see com.aetherteam.aether.mixin.mixins.client.BossHealthOverlayMixin#event(CustomizeGuiOverlayEvent.BossEventProgress)
     * @see GuiHooks#drawBossHealthBar(GuiGraphics, int, int, LerpingBossEvent)
     */
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        LerpingBossEvent bossEvent = event.getBossEvent();
        UUID bossUUID = bossEvent.getId();
        if (GuiHooks.isAetherBossBar(bossUUID)) {
            GuiHooks.drawBossHealthBar(guiGraphics, event.getX(), event.getY(), bossEvent);
            event.setIncrement(event.getIncrement() + 13);
        }
    }
}
