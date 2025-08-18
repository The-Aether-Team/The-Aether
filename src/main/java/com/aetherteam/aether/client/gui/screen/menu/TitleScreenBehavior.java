package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.mixin.mixins.client.accessor.AbstractWidgetAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

public interface TitleScreenBehavior {
    /**
     * Handles whether image buttons should be visible and handles their positioning offset in the top right corner of the screen.
     * The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
     *
     * @param titleScreen The parent {@link TitleScreen}.
     * @param xOffset     The {@link Integer} x-offset for the buttons.
     */
    default void handleImageButtons(TitleScreen titleScreen, int xOffset) {
        for (GuiEventListener renderable : titleScreen.children()) {
            if (renderable instanceof Button button) {
                Component buttonText = button.getMessage();
                if (buttonText.equals(Component.translatable("options.accessibility"))) {
                    button.setX(titleScreen.width - 48 + xOffset);
                    button.setY(4);
                } else if (buttonText.equals(Component.translatable("options.language"))) {
                    button.setX(titleScreen.width - 24 + xOffset);
                    button.setY(4);
                }
                if (TitleScreenBehavior.isImageButton(buttonText) && ((AbstractWidgetAccessor) button).aether$getAlpha() > 0.01) { // Alpha check fixes button offset bug when menu first opens.
                    button.visible = true;
                }
            }
        }
    }

    /**
     * Checks whether a button is one of the title screen image buttons.
     *
     * @param buttonText The button text {@link Component}.
     * @return The {@link Boolean} result.
     */
    static boolean isImageButton(Component buttonText) {
        return buttonText.equals(Component.translatable("options.accessibility"))
                || buttonText.equals(Component.translatable("options.language"));
    }

    /**
     * Checks whether a button belongs to the main set of title screen buttons.
     *
     * @param buttonText The button text {@link Component}.
     * @return The {@link Boolean} result.
     */
    static boolean isMainButton(Component buttonText) {
        return buttonText.equals(Component.translatable("menu.singleplayer"))
                || buttonText.equals(Component.translatable("menu.multiplayer"))
                || buttonText.equals(Component.translatable("gui.aether.menu.server"))
                || buttonText.equals(Component.translatable("menu.online"))
                || buttonText.equals(Component.translatable("fml.menu.mods"))
                || buttonText.equals(Component.translatable("menu.options"))
                || buttonText.equals(Component.translatable("menu.quit"));
    }
}
