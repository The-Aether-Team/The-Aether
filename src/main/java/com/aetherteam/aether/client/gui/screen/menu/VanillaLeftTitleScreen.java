package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.ClientHooks;

/**
 * A left-aligned variant of Minecraft's title screen.
 */
public class VanillaLeftTitleScreen extends TitleScreen implements TitleScreenBehavior {
    private static final ResourceLocation PANORAMA_OVERLAY = ResourceLocation.withDefaultNamespace("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    private final boolean showMinceraftEasterEgg = (double) RandomSource.create().nextFloat() < 1.0E-4D;

    public VanillaLeftTitleScreen() {
        ((TitleScreenAccessor) this).aether$setFading(true);
    }

    @Override
    protected void init() {
        super.init();
        this.setupButtons();
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
        TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
        if (this.minecraft != null && titleScreenAccessor.aether$getSplash() == null) {
            titleScreenAccessor.aether$setSplash(this.minecraft.getSplashManager().getSplash());
        }
        float fadeAmount = TitleScreenBehavior.super.handleFading(guiGraphics, this, titleScreenAccessor, this.panorama, PANORAMA_OVERLAY, partialTicks);
        this.renderLogo(guiGraphics, fadeAmount);
        int roundedFadeAmount = Mth.ceil(fadeAmount * 255.0F) << 24;
        if ((roundedFadeAmount & -67108864) != 0) {
            if (titleScreenAccessor.getWarningLabel() != null) {
                titleScreenAccessor.getWarningLabel().render(guiGraphics, roundedFadeAmount);
            }
            ClientHooks.renderMainMenu(this, guiGraphics, this.font, this.width, this.height, roundedFadeAmount);
            if (titleScreenAccessor.aether$getSplash() != null) {
                SplashRendererAccessor splashRendererAccessor = (SplashRendererAccessor) titleScreenAccessor.aether$getSplash();
                if (splashRendererAccessor.cumulus$getSplash() != null && !splashRendererAccessor.cumulus$getSplash().isEmpty()) {
                    PoseStack poseStack = guiGraphics.pose();
                    poseStack.pushPose();
                    poseStack.translate(250.0F, 50.0F, 0.0F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
                    float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
                    textSize = textSize * 100.0F / (float) (VanillaLeftTitleScreen.this.font.width(splashRendererAccessor.cumulus$getSplash()) + 32);
                    poseStack.scale(textSize, textSize, textSize);
                    guiGraphics.drawCenteredString(VanillaLeftTitleScreen.this.font, splashRendererAccessor.cumulus$getSplash(), 0, -8, 16776960 | roundedFadeAmount);
                    poseStack.popPose();
                }
            }
            TitleScreenBehavior.super.renderRightBranding(guiGraphics, this, this.font, roundedFadeAmount);
        }

        int xOffset = TitleScreenBehavior.super.handleButtonVisibility(this, fadeAmount);
        for (Renderable renderable : this.renderables) { // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
            renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
            if (renderable instanceof DynamicMenuButton dynamicMenuButton) {
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24;
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);
    }

    /**
     * [CODE COPY] - {@link LogoRenderer#renderLogo(GuiGraphics, int, float)}.
     */
    public void renderLogo(GuiGraphics guiGraphics, float transparency) {
        this.renderLogo(guiGraphics, transparency, 30);
    }

    /**
     * [CODE COPY] - {@link LogoRenderer#renderLogo(GuiGraphics, int, float, int)}.<br><br>
     * Modified to align the logo to the left instead of the center.
     */
    public void renderLogo(GuiGraphics guiGraphics, float transparency, int height) {
        int xOffset = 16;
        int yOffset = -11;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, transparency);
        guiGraphics.blit(this.showMinceraftEasterEgg ? LogoRenderer.EASTER_EGG_LOGO : LogoRenderer.MINECRAFT_LOGO, xOffset, yOffset + 22, 0.0F, 0.0F, 256, 44, 256, 64);
        guiGraphics.blit(LogoRenderer.MINECRAFT_EDITION, xOffset + 67, height + 37 + yOffset, 0.0F, 0.0F, 128, 14, 128, 16);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
