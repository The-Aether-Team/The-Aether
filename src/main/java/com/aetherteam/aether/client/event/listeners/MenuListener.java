package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.MenuHooks;
import com.aetherteam.cumulus.client.CumulusClient;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class MenuListener {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onGuiOpenHighest(ScreenEvent.Opening event) {
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
    }

    @SubscribeEvent
    public static void onGuiInitialize(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof TitleScreen titleScreen) {
            MenuHooks.setCustomSplashText(titleScreen);

            Button toggleWorldButton = MenuHooks.setupToggleWorldButton(screen);
            if (toggleWorldButton != null) {
                event.addListener(toggleWorldButton);
            }

            Button menuSwitchButton = MenuHooks.setupMenuSwitchButton(screen);
            if (menuSwitchButton != null) {
                event.addListener(menuSwitchButton);
            }

            Button quickLoadButton = MenuHooks.setupQuickLoadButton(screen);
            if (quickLoadButton != null) {
                event.addListener(quickLoadButton);
            }
        }
    }
}
