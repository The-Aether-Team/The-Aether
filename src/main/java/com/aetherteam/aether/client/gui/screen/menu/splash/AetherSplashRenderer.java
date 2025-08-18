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
    public float scale = 1.0F;

    public AetherSplashRenderer(boolean alignedLeft, String splash) {
        super(splash);
        this.alignedLeft = alignedLeft;
        this.splash = splash;
    }

    public void render(GuiGraphics guiGraphics, int screenWidth, Font font, int color) {
        guiGraphics.pose().pushPose();
        float splashX = this.alignedLeft ? 400.0F / this.scale : (float) screenWidth / 2 + (175 / this.scale);
        float splashY = this.alignedLeft ? 100.0F / this.scale : (int) (20 + (76 / this.scale));
        guiGraphics.pose().translate(splashX, splashY, 0.0F);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float textSize = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
        textSize = textSize * (200.0F / this.scale) / (font.width(this.splash) + (64 / scale));
        guiGraphics.pose().scale(textSize, textSize, textSize);
        guiGraphics.drawCenteredString(font, this.splash, 0, (int) (-16 / this.scale), 16776960 | color);
        guiGraphics.pose().popPose();
    }
}
