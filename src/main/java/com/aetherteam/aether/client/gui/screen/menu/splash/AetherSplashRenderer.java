package com.aetherteam.aether.client.gui.screen.menu.splash;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;

public class AetherSplashRenderer extends SplashRenderer {
    private final boolean alignedLeft;
    private final String splash;

    public AetherSplashRenderer(boolean alignedLeft, String splash) {
        super(splash);
        this.alignedLeft = alignedLeft;
        this.splash = splash;
    }

    public void render(GuiGraphics guiGraphics, int screenWidth, Font font, int color) {
        guiGraphics.pose().pushPose();
        float splashX = this.alignedLeft ? 205.0F : (screenWidth / 2.0F) + (165.0F / 2.0F);
        float splashY = this.alignedLeft ? 57.0F : 68.0F;
        guiGraphics.pose().translate(splashX, splashY, 0.0F);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
        textSize = textSize * 100.0F / 1 / (font.width(this.splash) + 32);
        guiGraphics.pose().scale(textSize, textSize, textSize);
        guiGraphics.drawCenteredString(font, this.splash, 0, -8, 16776960 | color);
        guiGraphics.pose().popPose();
    }
}
