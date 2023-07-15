package com.aetherteam.aether.client.gui.screen.menu;

import com.aetherteam.aether.client.gui.component.DynamicMenuButton;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.minecraftforge.internal.BrandingControl;

import javax.annotation.Nonnull;

public class VanillaLeftTitleScreen extends TitleScreen {
    private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

    private TitleScreenModUpdateIndicator modUpdateNotification;

    public VanillaLeftTitleScreen() {
        ((TitleScreenAccessor) this).aether$setFading(true);
    }

    @Override
    protected void init() {
        super.init();
        this.setupButtons();
    }

    public void setupButtons() {
        int buttonCount = 0;
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof Button button) {
                Component buttonText = button.getMessage();
                if (isButtonLeft(buttonText)) {
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

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
        if (this.minecraft != null) {
            if (titleScreenAccessor.aether$getFadeInStart() == 0L && titleScreenAccessor.aether$isFading()) {
                titleScreenAccessor.aether$setFadeInStart(Util.getMillis());
            }

            float f = titleScreenAccessor.aether$isFading() ? (float)(Util.getMillis() - titleScreenAccessor.aether$getFadeInStart()) / 1000.0F : 1.0F;
            this.panorama.render(partialTicks, Mth.clamp(f, 0.0F, 1.0F));
            RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, titleScreenAccessor.aether$isFading() ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
            blit(poseStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            float f1 = titleScreenAccessor.aether$isFading() ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
            ((TitleScreenAccessor)this).aether$getLogoRenderer().renderLogo(poseStack, this.width, f1);
            int l = Mth.ceil(f1 * 255.0F) << 24;
            if ((l & -67108864) != 0) {
                if (((TitleScreenAccessor)this).getWarningLabel() != null) {
                    ((TitleScreenAccessor)this).getWarningLabel().render(poseStack, l);
                }
                net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, poseStack, this.font, this.width, this.height, l);
                if (titleScreenAccessor.aether$getSplash() != null) {
                    poseStack.pushPose();
                    poseStack.translate(250.0F, 50.0F, 0.0F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
                    float f2 = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
                    f2 = f2 * 100.0F / (float) (this.font.width(titleScreenAccessor.aether$getSplash()) + 32); poseStack.scale(f2, f2, f2);
                    drawCenteredString(poseStack, this.font, titleScreenAccessor.aether$getSplash(), 0, -8, 16776960 | l);
                    poseStack.popPose();
                }

                BrandingControl.forEachLine(true, true, (brdline, brd) ->
                        drawString(poseStack, this.font, brd, width - this.font.width(brd) - 1, this.height - (10 + (brdline + 1) * (this.font.lineHeight + 1)), 16777215 | l)
                );
                BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
                        drawString(poseStack, this.font, brd, 1, this.height - (brdline + 1) * (this.font.lineHeight + 1), 16777215 | l)
                );
            }

            for (GuiEventListener guieventlistener : this.children()) {
                if (guieventlistener instanceof AbstractWidget abstractWidget) {
                    if (f1 > 0.02F) {
                        abstractWidget.setAlpha(f1);
                        abstractWidget.visible = true;
                    } else {
                        abstractWidget.visible = false;
                    }
                }
            }

            int offset = 0;
            for (Renderable renderable : this.renderables) {
                renderable.render(poseStack, mouseX, mouseY, partialTicks);
                if (renderable instanceof DynamicMenuButton dynamicMenuButton) {
                    if (dynamicMenuButton.enabled) {
                        offset -= 24;
                    }
                }
            }
            for (Renderable renderable : this.renderables) {
                if (renderable instanceof Button button) {
                    Component buttonText = button.getMessage();
                    if (buttonText.equals(Component.translatable("narrator.button.accessibility"))) {
                        button.setX(this.width - 48 + offset);
                        button.setY(4);
                    } else if (buttonText.equals(Component.translatable("narrator.button.language"))) {
                        button.setX(this.width - 24 + offset);
                        button.setY(4);
                    }
                }
            }

            if (f1 >= 1.0f) {
                this.modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
            }
        }
    }

    public boolean isButtonLeft(Component buttonText) {
        return buttonText.equals(Component.translatable("menu.singleplayer"))
                || buttonText.equals(Component.translatable("menu.multiplayer"))
                || buttonText.equals(Component.translatable("menu.online"))
                || buttonText.equals(Component.translatable("fml.menu.mods"))
                || buttonText.equals(Component.translatable("menu.options"))
                || buttonText.equals(Component.translatable("menu.quit"));
    }
}
