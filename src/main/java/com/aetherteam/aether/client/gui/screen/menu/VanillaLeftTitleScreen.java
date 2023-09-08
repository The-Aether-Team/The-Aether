package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiComponent;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;

/**
 * A left-aligned variant of Minecraft's title screen.
 */
public class VanillaLeftTitleScreen extends TitleScreen implements TitleScreenBehavior {
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private final PanoramaRenderer panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    private final boolean showMinceraftEasterEgg = (double) RandomSource.create().nextFloat() < 1.0E-4D;
    private TitleScreenModUpdateIndicator modUpdateNotification;

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
                        if (button.getMessage().equals(Component.translatable("fml.menu.mods"))) {
                            this.modUpdateNotification = TitleScreenModUpdateIndicator.init(this, button);
                        }
                    }
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link TitleScreen#render(PoseStack, int, int, float)}.<br><br>
     * Modified and abstracted using {@link TitleScreenBehavior}.
     */
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
        float fadeAmount = TitleScreenBehavior.super.handleFading(poseStack, this, titleScreenAccessor, this.panorama, PANORAMA_OVERLAY, partialTicks);
        this.renderLogo(poseStack, fadeAmount);
        int roundedFadeAmount = Mth.ceil(fadeAmount * 255.0F) << 24;
        if ((roundedFadeAmount & -67108864) != 0) {
            if (titleScreenAccessor.getWarningLabel() != null) {
                titleScreenAccessor.getWarningLabel().render(poseStack, roundedFadeAmount);
            }
            ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, roundedFadeAmount);
            if (titleScreenAccessor.aether$getSplash() != null) {
                poseStack.pushPose();
                poseStack.translate(250.0F, 50.0F, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
                float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
                textSize = textSize * 100.0F / (float) (this.font.width(titleScreenAccessor.aether$getSplash()) + 32); poseStack.scale(textSize, textSize, textSize);
                GuiComponent.drawCenteredString(poseStack, this.font, titleScreenAccessor.aether$getSplash(), 0, -8, 16776960 | roundedFadeAmount);
                poseStack.popPose();
            }
            TitleScreenBehavior.super.renderRightBranding(poseStack, this, this.font, roundedFadeAmount);
        }

        int xOffset = TitleScreenBehavior.super.handleButtonVisibility(this, fadeAmount);
        for (Renderable renderable : this.renderables) { // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
            renderable.render(poseStack, mouseX, mouseY, partialTicks);
            if (renderable instanceof DynamicMenuButton dynamicMenuButton) {
                if (dynamicMenuButton.enabled) {
                    xOffset -= 24;
                }
            }
        }
        TitleScreenBehavior.super.handleImageButtons(this, xOffset);

        if (fadeAmount >= 1.0F) {
            this.modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    /**
     * [CODE COPY] - {@link LogoRenderer#renderLogo(PoseStack, int, float)}.
     */
    public void renderLogo(PoseStack poseStack, float transparency) {
        this.renderLogo(poseStack, transparency, 30);
    }

    /**
     * [CODE COPY] - {@link LogoRenderer#renderLogo(PoseStack, int, float, int)}.<br><br>
     * Modified to align the logo to the left instead of the center.
     */
    public void renderLogo(PoseStack poseStack, float transparency, int height) {
        RenderSystem.setShaderTexture(0, LogoRenderer.MINECRAFT_LOGO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, transparency);
        int xOffset = 11;
        int yOffset = -11;
        if (this.showMinceraftEasterEgg) {
            GuiComponent.blitOutlineBlack(xOffset, height, (x, y) -> {
                GuiComponent.blit(poseStack, x, y + yOffset, 0, 0, 99, 44);
                GuiComponent.blit(poseStack, x + 99, y + yOffset, 129, 0, 27, 44);
                GuiComponent.blit(poseStack, x + 99 + 26, y + yOffset, 126, 0, 3, 44);
                GuiComponent.blit(poseStack, x + 99 + 26 + 3, y + yOffset, 99, 0, 26, 44);
                GuiComponent.blit(poseStack, x + 155, y + yOffset, 0, 45, 155, 44);
            });
        } else {
            GuiComponent.blitOutlineBlack(xOffset, height, (x, y) -> {
                GuiComponent.blit(poseStack, x, y + yOffset, 0, 0, 155, 44);
                GuiComponent.blit(poseStack, x + 155, y + yOffset, 0, 45, 155, 44);
            });
        }
        RenderSystem.setShaderTexture(0, LogoRenderer.MINECRAFT_EDITION);
        GuiComponent.blit(poseStack, xOffset + 88, height + 37 + yOffset, 0.0F, 0.0F, 98, 14, 128, 16);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
