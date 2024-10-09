package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.internal.BrandingControl;

public interface TitleScreenBehavior {
    /**
     * [CODE COPY] - {@link TitleScreen#render(GuiGraphics, int, int, float)}.<br><br>
     * Copied fading behavior segment from render code, with use of {@link TitleScreenAccessor}.
     */
    default float handleFading(GuiGraphics guiGraphics, TitleScreen titleScreen, TitleScreenAccessor titleScreenAccessor, PanoramaRenderer panorama, ResourceLocation panoramaOverlay, float partialTicks) {
        if (titleScreenAccessor.aether$getFadeInStart() == 0L && titleScreenAccessor.aether$isFading()) {
            titleScreenAccessor.aether$setFadeInStart(Util.getMillis());
        }
        float fadeAmount = titleScreenAccessor.aether$isFading() ? (float) (Util.getMillis() - titleScreenAccessor.aether$getFadeInStart()) / 1000.0F : 1.0F;
        panorama.render(guiGraphics, titleScreen.width, titleScreen.height, Mth.clamp(fadeAmount, 0.0F, 1.0F), partialTicks);
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, titleScreenAccessor.aether$isFading() ? (float) Mth.ceil(Mth.clamp(fadeAmount, 0.0F, 1.0F)) : 1.0F);
        guiGraphics.blit(panoramaOverlay, 0, 0, titleScreen.width, titleScreen.height, 0.0F, 0.0F, 16, 128, 16, 128);
        return titleScreenAccessor.aether$isFading() ? Mth.clamp(fadeAmount - 1.0F, 0.0F, 1.0F) : 1.0F;
    }

    /**
     * [CODE COPY] - {@link TitleScreen#render(GuiGraphics, int, int, float)}.<br><br>
     * Copied branding render segment from render code, but aligned it right.
     */
    default void renderRightBranding(GuiGraphics guiGraphics, TitleScreen titleScreen, Font font, int roundedFadeAmount) {
        BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
                guiGraphics.drawString(font, branding, titleScreen.width - font.width(branding) - 1, titleScreen.height - (10 + (brandingLine + 1) * (font.lineHeight + 1)), 16777215 | roundedFadeAmount)
        );
        BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
                guiGraphics.drawString(font, branding, 1, titleScreen.height - (brandingLine + 1) * (font.lineHeight + 1), 16777215 | roundedFadeAmount)
        );
    }

    /**
     * [CODE COPY] - {@link TitleScreen#render(GuiGraphics, int, int, float)}.<br><br>
     * Copied render segment for determining button transparency from screen fade-in.
     * Also modified the code to change the button visibility, and also set a button offset at the end from configs.
     */
    default int handleButtonVisibility(TitleScreen titleScreen, float fadeAmount) {
        for (GuiEventListener guiEventListener : titleScreen.children()) {
            if (guiEventListener instanceof AbstractWidget abstractWidget) {
                if (fadeAmount > 0.02F) {
                    if (!TitleScreenBehavior.isImageButton(abstractWidget.getMessage())) {
                        abstractWidget.setAlpha(fadeAmount);
                        abstractWidget.visible = true;
                    }
                } else {
                    abstractWidget.visible = false;
                }
            }
        }
        return CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
    }

    /**
     * Handles whether image buttons should be visible and handles their positioning offset in the top right corner of the screen.
     * The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
     *
     * @param titleScreen The parent {@link TitleScreen}.
     * @param xOffset     The {@link Integer} x-offset for the buttons.
     */
    default void handleImageButtons(TitleScreen titleScreen, int xOffset) {
        for (Renderable renderable : titleScreen.renderables) {
            if (renderable instanceof Button button) {
                Component buttonText = button.getMessage();
                if (TitleScreenBehavior.isImageButton(buttonText)) {
                    button.visible = true;
                }
                if (buttonText.equals(Component.translatable("options.accessibility"))) {
                    button.setX(titleScreen.width - 48 + xOffset);
                    button.setY(4);
                } else if (buttonText.equals(Component.translatable("options.language"))) {
                    button.setX(titleScreen.width - 24 + xOffset);
                    button.setY(4);
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
