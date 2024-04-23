package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.event.hooks.MenuHooks;
import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.CumulusClient;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.List;

public class MenuListener {
    /**
     * @see MenuHooks#prepareCustomMenus(MenuHelper)
     */
    public static void onGuiOpenHighest() {
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#setupToggleWorldButton(Screen)
     * @see MenuHooks#setupMenuSwitchButton(Screen)
     * @see MenuHooks#setupQuickLoadButton(Screen)
     */
    public static void onGuiInitialize(Minecraft client, Screen screen, int scaledWidth, int scaledHeight) {
        if (screen instanceof TitleScreen titleScreen) {
            MenuHooks.setCustomSplashText(titleScreen);
            List<AbstractWidget> buttons = Screens.getButtons(screen);

            Button toggleWorldButton = MenuHooks.setupToggleWorldButton(screen);
            if (toggleWorldButton != null) {
                buttons.add(toggleWorldButton);
            }

            Button menuSwitchButton = MenuHooks.setupMenuSwitchButton(screen);
            if (menuSwitchButton != null) {
                buttons.add(menuSwitchButton);
            }

            Button quickLoadButton = MenuHooks.setupQuickLoadButton(screen);
            if (quickLoadButton != null) {
                buttons.add(quickLoadButton);
            }
        }
    }

    public static void init() {
        ScreenEvents.AFTER_INIT.register(MenuListener::onGuiInitialize);
    }
}
