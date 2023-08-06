package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.client.AetherMenuUtil;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.CumulusClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.Calendar;
import java.util.function.Predicate;

public class MenuHooks {
    /**
     * Prepares the Aether's registered custom menus in the order to check whether they should be displayed or not.
     * @param menuHelper The {@link MenuHelper} for the menu system.
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiOpenHighest(ScreenEvent.Opening)
     */
    public static void prepareCustomMenus(MenuHelper menuHelper) {
        menuHelper.prepareMenu(AetherMenus.MINECRAFT_LEFT.get());
        menuHelper.prepareMenu(AetherMenus.THE_AETHER.get());
        menuHelper.prepareMenu(AetherMenus.THE_AETHER_LEFT.get());
    }

    /**
     * If the current date is July 22nd, displays the Aether's anniversary splash text.
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static void setCustomSplashText(TitleScreen screen) {
        Predicate<Calendar> condition = (calendar) -> calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DATE) == 22;
        CumulusClient.MENU_HELPER.setCustomSplash(screen, condition, "Happy anniversary to the Aether!");
    }

    /**
     * Sets up the button for toggling the world preview display.
     * @param screen The current {@link Screen}.
     * @return The created {@link Button}.
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static Button setupToggleWorldButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(new Button.Builder(Component.translatable("gui.aether.menu.button.world_preview"), (pressed) -> {
                AetherConfig.CLIENT.enable_world_preview.set(!AetherConfig.CLIENT.enable_world_preview.get());
                AetherConfig.CLIENT.enable_world_preview.save();
                WorldDisplayHelper.toggleWorldPreview();
            }).bounds(screen.width - 24 - getButtonOffset(), 4, 20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.menu.preview"))));
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            return dynamicMenuButton;
        }
        return null;
    }

    /**
     * Sets up the button for toggling between the Aether and Minecraft menu themes.
     * @param screen The current {@link Screen}.
     * @return The created {@link Button}.
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static Button setupMenuSwitchButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(new Button.Builder(Component.translatable("gui.aether.menu.button.theme"), (pressed) -> {
                String menu = toggleBetweenMenus();
                if (menu != null) {
                    CumulusConfig.CLIENT.active_menu.set(menu);
                    CumulusConfig.CLIENT.active_menu.save();
                }
                CumulusClient.MENU_HELPER.setShouldFade(true);
                Minecraft.getInstance().setScreen(CumulusClient.MENU_HELPER.applyMenu(CumulusClient.MENU_HELPER.getActiveMenu()));
                Minecraft.getInstance().getMusicManager().stopPlaying();
                AetherMusicManager.stopPlaying();
            }).bounds(screen.width - 24 - getButtonOffset(), 4, 20, 20).tooltip(Tooltip.create(Component.translatable(AetherMenuUtil.isAetherMenu() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether"))));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_aether_menu_button);
            return dynamicMenuButton;
        }
        return null;
    }

    /**
     * Sets up the button for quick-loading into a world when the world preview is active.
     * @param screen The current {@link Screen}.
     * @return The created {@link Button}.
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static Button setupQuickLoadButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(new Button.Builder(Component.translatable("gui.aether.menu.button.quick_load"), (pressed) -> {
                WorldDisplayHelper.enterLoadedLevel();
                Minecraft.getInstance().getMusicManager().stopPlaying();
                Minecraft.getInstance().getSoundManager().stop();
                AetherMusicManager.stopPlaying();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }).bounds(screen.width - 24 - getButtonOffset(), 4, 20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.menu.load"))));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button, AetherConfig.CLIENT.enable_aether_menu_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview, AetherConfig.CLIENT.enable_quick_load_button);
            return dynamicMenuButton;
        }
        return null;
    }

    /**
     * Toggles between Aether and Minecraft menus, with the default menus to toggle to determined by
     * {@link AetherConfig.Client#default_minecraft_menu} and {@link AetherConfig.Client#default_aether_menu}.
     * @return The {@link String} for the menu's ID.
     */
    private static String toggleBetweenMenus() {
        if (AetherMenuUtil.isAetherMenu()) {
            return AetherConfig.CLIENT.default_minecraft_menu.get();
        } else if (AetherMenuUtil.isMinecraftMenu()) {
            return AetherConfig.CLIENT.default_aether_menu.get();
        } else {
            return null;
        }
    }

    /**
     * @return An {@link Integer} offset for buttons, dependent on whether Cumulus' menu switcher button is enabled,
     * as determined by {@link CumulusConfig.Client#enable_menu_api} and {@link CumulusConfig.Client#enable_menu_list_button}.
     */
    private static int getButtonOffset() {
        return CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? 62 : 0;
    }
}
