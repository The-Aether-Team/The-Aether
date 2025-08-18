package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.component.menu.AetherMenuButton;
import com.aetherteam.aether.client.gui.screen.menu.logo.AetherLogoRenderer;
import com.aetherteam.aether.client.gui.screen.menu.splash.AetherSplashRenderer;
import com.aetherteam.aether.client.gui.screen.menu.splash.LeftSplashRenderer;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.gui.screen.DynamicMenuButton;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.neoforged.neoforge.internal.BrandingControl;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class AetherTitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU, 20, 600, true);
    private final boolean alignedLeft;
    private int rows;

    public AetherTitleScreen() {
        this(false);
    }

    public AetherTitleScreen(boolean alignedLeft) {
        super();
        this.alignedLeft = alignedLeft;
        TitleScreenAccessor accessor = ((TitleScreenAccessor) this);
        accessor.aether$setFading(true);
        accessor.aether$setLogoRenderer(new AetherLogoRenderer(false, this.alignedLeft));
    }

    @Override
    protected void init() {
        TitleScreenAccessor accessor = (TitleScreenAccessor) this;
        float scale = getScale(this, this.getMinecraft());
        if (accessor.aether$getLogoRenderer() instanceof AetherLogoRenderer aetherLogoRenderer) {
            aetherLogoRenderer.scale = scale;
        }
        super.init();
        if (this.minecraft != null) {
            accessor.aether$setSplash(new AetherSplashRenderer(this.alignedLeft, ((SplashRendererAccessor) ((TitleScreenAccessor) this).aether$getSplash()).cumulus$getSplash()));
            if (accessor.aether$getSplash() instanceof AetherSplashRenderer aetherSplashRenderer) {
                aetherSplashRenderer.scale = scale;
            }
        }
        this.setupButtons();
    }

    public void setupButtons() {
        int buttonRows = 0;
        int lastY = 0;
        if (AetherConfig.CLIENT.enable_server_button.get()) {
            Component component = ((TitleScreenAccessor) this).callGetMultiplayerDisabledReason();
            boolean flag = component == null;
            Tooltip tooltip = component != null ? Tooltip.create(component) : null;
            Button serverButton = this.addRenderableWidget(Button.builder(Component.translatable("gui.aether.menu.server"), (button) -> {
                ServerData serverData = new ServerData("OATS", "oats.aether-mod.net", ServerData.Type.OTHER);
                ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(serverData.ip), serverData, false, null);
            }).bounds(this.width / 2 - 100, (this.height / 4 + 48) + 24 * 3, 200, 20).tooltip(tooltip).build());
            serverButton.active = flag;
            Predicate<AbstractWidget> predicate = (abstractWidget) -> (abstractWidget.getMessage().equals(Component.translatable("menu.multiplayer")) || abstractWidget.getMessage().equals(Component.translatable("menu.online")));
            this.children().removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
            this.renderables.removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
        }
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof AbstractWidget abstractWidget) {
                Component buttonText = abstractWidget.getMessage();
                if (TitleScreenBehavior.isImageButton(buttonText)) {
                    abstractWidget.visible = false; // The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
                }
                if (abstractWidget instanceof AetherMenuButton aetherMenuButton) { // Sets button values that determine their positioning on the screen.
                    if (this.isAlignedLeft()) {
                        buttonRows++;
                    } else {
                        if (lastY < aetherMenuButton.originalY) {
                            lastY = aetherMenuButton.originalY;
                            buttonRows++;
                        }
                    }
                    if (buttonText.equals(Component.translatable("gui.aether.menu.server"))) {
                        aetherMenuButton.serverButton = true;
                        aetherMenuButton.buttonCountOffset = 2;
                    } else {
                        aetherMenuButton.buttonCountOffset = buttonRows;
                    }
                    if (AetherConfig.CLIENT.enable_server_button.get() && buttonText.equals(Component.translatable("menu.singleplayer"))) {
                        buttonRows++;
                    }
                }
            }
        }
        this.rows = this.alignedLeft ? buttonRows : buttonRows - 1;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int xOffset = CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
        for (GuiEventListener child : this.children()) {
            if (child instanceof AetherMenuButton aetherButton) { // Smoothly shifts the Aether-styled buttons to the right slightly when hovered over.
                if (aetherButton.isMouseOver(mouseX, mouseY)) {
                    if (aetherButton.hoverOffset < 15) {
                        aetherButton.hoverOffset += 4;
                    }
                } else {
                    if (aetherButton.hoverOffset > 0) {
                        aetherButton.hoverOffset -= 4;
                    }
                }
            }
            if (child instanceof DynamicMenuButton dynamicMenuButton) {  // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24; //todo figure out oddness
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
    }

    @Override
    public boolean forEachLineBranding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        if (this.alignedLeft) {
            BrandingControl.forEachLine(true, true, (brandingLine, branding) ->
                guiGraphics.drawString(font, branding, this.width - font.width(branding) - 1, this.height - (10 + (brandingLine + 1) * (font.lineHeight + 1)), 16777215 | i)
            );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean forEachAboveCopyrightLineBranding(BiConsumer<Integer, String> lineConsumer, GuiGraphics guiGraphics, int i) {
        if (this.alignedLeft) {
            BrandingControl.forEachAboveCopyrightLine((brandingLine, branding) ->
                guiGraphics.drawString(font, branding, 1, this.height - (brandingLine + 1) * (font.lineHeight + 1), 16777215 | i)
            );
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines the proper scaling for menu elements relative to the true screen scale.
     *
     * @param screen    The parent {@link AetherTitleScreen}.
     * @param minecraft The {@link Minecraft} instance.
     * @return The {@link Float} scale for menu elements.
     */
    public static float getScale(AetherTitleScreen screen, Minecraft minecraft) {
        int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());  // The true screen GUI scale.
        return calculateScale(screen, guiScale, guiScale - 1);
    }

    /**
     * Determines the proper scaling for menu elements relative to the given scale factors.
     *
     * @param screen     The parent {@link AetherTitleScreen}.
     * @param guiScale   The base GUI scale {@link Float}.
     * @param lowerScale A GUI scale {@link Float} value that is one less than the base.
     * @return The {@link Float} scale for menu elements.
     */
    public static float calculateScale(AetherTitleScreen screen, float guiScale, float lowerScale) {
        float scale = 1.0F;
        if (guiScale > 1) {
            scale = guiScale / lowerScale; // A scale factor to counteract the GUI scale option's changing of menu element's pixel scale (pixels-per-pixel).
        }
        int range = AetherMenuButton.totalHeightRange(screen.rows, scale);
        if (range > screen.height && scale != 1.0F) { // Recursive check to see if the menu elements can actually fit on the screen, otherwise it'll try to shrink to a lower GUI scale.
            return calculateScale(screen, guiScale, lowerScale - 1);
        } else {
            return scale;
        }
    }

    /**
     * Changes main menu buttons into Aether-styled main menu buttons.<br><br>
     * Warning for "unchecked" is suppressed because the buttons should always be able to be cast.
     *
     * @param renderable A renderable widget.
     * @return A new renderable widget.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T renderable) {
        if (renderable instanceof Button button) {
            if (TitleScreenBehavior.isMainButton(button.getMessage())) {
                AetherMenuButton aetherButton = new AetherMenuButton(this, button);
                return (T) super.addRenderableWidget(aetherButton);
            }
        }
        return super.addRenderableWidget(renderable);
    }

    public boolean isAlignedLeft() {
        return this.alignedLeft;
    }
}
