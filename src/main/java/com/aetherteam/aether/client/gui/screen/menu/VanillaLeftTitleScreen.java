package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.client.gui.screen.menu.logo.LeftLogoRenderer;
import com.aetherteam.aether.client.gui.screen.menu.splash.LeftSplashRenderer;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.gui.screen.DynamicMenuButton;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.internal.BrandingControl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * A left-aligned variant of Minecraft's title screen.
 */
public class VanillaLeftTitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    private Map<Component, AbstractWidget> widgetsByName = new HashMap<>();

    public VanillaLeftTitleScreen() {
        super();
        TitleScreenAccessor accessor = ((TitleScreenAccessor) this);
        accessor.aether$setFading(true);
        accessor.aether$setLogoRenderer(new LeftLogoRenderer(false));
    }

    @Override
    protected void init() {
        TitleScreenAccessor accessor = (TitleScreenAccessor) this;
        if (accessor.aether$getLogoRenderer() instanceof LeftLogoRenderer leftLogoRenderer) {
            leftLogoRenderer.screenWidth = this.width;
        }
        super.init();
        if (this.minecraft != null) {
            accessor.aether$setSplash(new LeftSplashRenderer(((SplashRendererAccessor) ((TitleScreenAccessor) this).aether$getSplash()).cumulus$getSplash()));
        }
        this.setupButtons();
        this.widgetsByName = this.children().stream().filter(e -> e instanceof AbstractWidget).map(e -> (AbstractWidget) e)
            .collect(Collectors.toMap(AbstractWidget::getMessage, e -> e));
    }

    /**
     * Aligns all buttons to the left.
     */
    public void setupButtons() {
        int buttonCount = 0;
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof AbstractWidget abstractWidget) {
                if (TitleScreenBehavior.isImageButton(abstractWidget.getMessage())) {
                    abstractWidget.visible = false; // The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
                }
                if (abstractWidget instanceof Button button) { // Left alignment.
                    Component buttonText = button.getMessage();
                    if (TitleScreenBehavior.isMainButton(buttonText)) {
                        button.setX(47);
                        button.setY(80 + buttonCount * 25);
                        button.setWidth(200);
                        buttonCount++;
                    }
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link TitleScreen#render(GuiGraphics, int, int, float)}.<br><br>
     * Modified and abstracted using {@link TitleScreenBehavior}.
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int xOffset = CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
        for (GuiEventListener child : this.children()) { // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
            if (child instanceof DynamicMenuButton dynamicMenuButton) {
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24;
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
        TitleScreenBehavior.super.handleEssentialButtonsForLeftMenu(this);
    }

    @Override
    public boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
            guiGraphics.drawString(font, branding, this.width - font.width(branding) - 1, this.height - (10 + (brandingLine + 1) * (font.lineHeight + 1)), 16777215 | i)
        );
        return true;
    }

    @Override
    public boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
            guiGraphics.drawString(font, branding, 1, this.height - (brandingLine + 1) * (font.lineHeight + 1), 16777215 | i)
        );
        return true;
    }

    @Override
    public Map<Component, AbstractWidget> getWidgetsByName() {
        return this.widgetsByName;
    }
}
