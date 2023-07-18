package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.api.AetherMenuUtil;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.api.WorldDisplayHelper;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.client.gui.component.DynamicMenuButton;
import com.aetherteam.nitrogen.NitrogenConfig;
import com.aetherteam.nitrogen.api.menu.MenuHelper;
import com.aetherteam.nitrogen.client.NitrogenClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import java.util.Calendar;
import java.util.function.Predicate;

public class MenuHooks {
    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(AetherMenus.MINECRAFT_LEFT.get());
        menuHelper.prepareMenu(AetherMenus.THE_AETHER.get());
        menuHelper.prepareMenu(AetherMenus.THE_AETHER_LEFT.get());
    }

    /**
     * If the current date is July 22nd, displays the Aether's anniversary splash text.
     */
    public static void setCustomSplashText(TitleScreen screen) {
        Predicate<Calendar> condition = (calendar) -> calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DATE) == 22;
        NitrogenClient.MENU_HELPER.setCustomSplash(screen, condition, "Happy anniversary to the Aether!");
    }

    //todo
    //  there should also be a way to override the menu button displays with nitrogen's menu selection display
    public static Button setupToggleWorldButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.world_preview"),
                    (pressed) -> {
                        AetherConfig.CLIENT.enable_world_preview.set(!AetherConfig.CLIENT.enable_world_preview.get());
                        AetherConfig.CLIENT.enable_world_preview.save();
                        WorldDisplayHelper.toggleWorldPreview(AetherConfig.CLIENT.enable_world_preview.get());
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.menu.preview")));
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            return dynamicMenuButton;
        }
        return null;
    }

    public static Button setupMenuSwitchButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.theme"),
                    (pressed) -> {
                        String menu = toggleBetweenMenus();
                        if (menu != null) {
                            NitrogenConfig.CLIENT.active_menu.set(menu);
                            NitrogenConfig.CLIENT.active_menu.save();
                        }
                        NitrogenClient.MENU_HELPER.setShouldFade(true);
                        Minecraft.getInstance().setScreen(NitrogenClient.MENU_HELPER.applyMenu(NitrogenClient.MENU_HELPER.getActiveMenu()));
                        Minecraft.getInstance().getMusicManager().stopPlaying();
                        AetherMusicManager.stopPlaying();
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable(AetherMenuUtil.isAetherMenu() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether")));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_aether_menu_button);
            return dynamicMenuButton;
        }
        return null;
    }

    public static Button setupQuickLoadButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.quick_load"),
                    (pressed) -> {
                        WorldDisplayHelper.quickLoad();
                        Minecraft.getInstance().getMusicManager().stopPlaying();
                        AetherMusicManager.stopPlaying(); //todo doesn't quite work. might need to stop it through the sound manager
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.menu.load")));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button, AetherConfig.CLIENT.enable_aether_menu_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview, AetherConfig.CLIENT.enable_quick_load_button);
            return dynamicMenuButton;
        }
        return null;
    }

    private static String toggleBetweenMenus() {
        if (AetherMenuUtil.isAetherMenu()) {
            return AetherConfig.CLIENT.default_minecraft_menu.get();
        } else if (AetherMenuUtil.isMinecraftMenu()) {
            return AetherConfig.CLIENT.default_aether_menu.get();
        } else {
            return null;
        }
    }


}
