package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.component.menu.AetherMenuButton;
import com.aetherteam.aether.client.gui.screen.menu.logo.AetherLogoRenderer;
import com.aetherteam.aether.client.gui.screen.menu.splash.AetherSplashRenderer;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.gui.screen.DynamicMenuButton;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AetherTitleScreen extends TitleScreen implements TitleScreenBehavior, CustomBranding {
    public static final Music MENU = new Music(AetherSoundEvents.MUSIC_MENU, 20, 600, true);
    private final boolean alignedLeft;
    private Map<Component, AbstractWidget> widgetsByName = new HashMap<>();

    public int buttonRows = 0;
    public int lastY = 0;

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
        this.buttonRows = 0;
        this.lastY = 0;
        super.init();
        if (this.minecraft != null) {
            accessor.aether$setSplash(new AetherSplashRenderer(this.alignedLeft, ((SplashRendererAccessor) ((TitleScreenAccessor) this).aether$getSplash()).cumulus$getSplash()));
        }
        this.setupButtons();
        this.widgetsByName = this.children().stream().filter(e -> e instanceof AbstractWidget).map(e -> (AbstractWidget) e)
            .collect(Collectors.toMap(AbstractWidget::getMessage, e -> e));
    }

    public void setupButtons() {
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
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int xOffset = CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? -62 : 0;
        for (GuiEventListener child : this.children()) {
            if (child instanceof AetherMenuButton aetherButton) { // Smoothly shifts the Aether-styled buttons to the right slightly when hovered over.
                if (aetherButton.isMouseOver(mouseX, mouseY)) {
                    if (aetherButton.hoverOffset < 15) {
                        aetherButton.hoverOffset += 2;
                    }
                } else {
                    if (aetherButton.hoverOffset > 0) {
                        aetherButton.hoverOffset -= 2;
                    }
                }
            }
            if (child instanceof DynamicMenuButton dynamicMenuButton) {  // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24;
                }
            }
        } //todo branding
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
        if (this.alignedLeft) {
            TitleScreenBehavior.super.handleEssentialButtonsForLeftMenu(this);
        }
    }

    @Override
    public int getBrandingTextX(Font font, String text, int x) {
        if (this.alignedLeft) {
            return this.width - font.width(text) - 2;
        } else {
            return x;
        }
    }

    @Override
    public int getBrandingTextY(Font font, String text, int y) {
        if (this.alignedLeft) {
            int creditsY = this.renderables.stream()
                .filter(renderable -> renderable instanceof PlainTextButton btn && btn.getMessage().equals(Component.translatable("title.credits")))
                .findFirst()
                .map(renderable -> ((PlainTextButton) renderable).getY())
                .orElse(0);
            return creditsY - font.lineHeight - 2;
        } else {
            return y;
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
            return (T) super.addRenderableWidget(this.getAetherButton(button));
        }
        return super.addRenderableWidget(renderable);
    }

    public boolean isAlignedLeft() {
        return this.alignedLeft;
    }


    @Override
    public AbstractWidget nitrogen_fabric$onScreensWidgetAdd(AbstractWidget abstractWidget) {
        if (abstractWidget instanceof Button button) {
            return this.getAetherButton(button);
        }
        return abstractWidget;
    }

    private Button getAetherButton(Button button) {
        if (TitleScreenBehavior.isMainButton(button)) {
            AetherMenuButton aetherButton = new AetherMenuButton(this, button);
            Component buttonText = aetherButton.getMessage();

            // Sets button values that determine their positioning on the screen.
            if (this.isAlignedLeft()) {
                this.buttonRows++;
            } else {
                if (this.lastY < aetherButton.originalY) {
                    this.lastY = aetherButton.originalY;
                    this.buttonRows++;
                }
            }
            if (buttonText.equals(Component.translatable("gui.aether.menu.server"))) {
                aetherButton.serverButton = true;
                aetherButton.buttonCountOffset = 2;
            } else {
                aetherButton.buttonCountOffset = this.buttonRows;
            }
            if (AetherConfig.CLIENT.enable_server_button.get() && buttonText.equals(Component.translatable("menu.singleplayer"))) {
                this.buttonRows++;
            }
            if (this.isAlignedLeft()) { // Changes button positioning dependent on whether the parent title screen is aligned left or not.
                aetherButton.setX(16);
                aetherButton.setY(50 + aetherButton.buttonCountOffset * 25);
                aetherButton.setWidth(200);
            } else {
                aetherButton.setY(this.height / 4 + 31 + 25 * (aetherButton.buttonCountOffset - 1));
            }
            return aetherButton;
        }
        return button;
    }

    @Override
    public Map<Component, AbstractWidget> getWidgetsByName() {
        return this.widgetsByName;
    }
}
