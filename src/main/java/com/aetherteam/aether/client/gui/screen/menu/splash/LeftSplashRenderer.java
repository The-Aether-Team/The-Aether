package com.aetherteam.aether.client.gui.screen.menu.splash;

import com.aetherteam.aether.client.gui.screen.menu.CustomPosition;
import net.minecraft.client.gui.components.SplashRenderer;

public class LeftSplashRenderer extends SplashRenderer implements CustomPosition {
    public LeftSplashRenderer(String splash) {
        super(splash);
    }

    @Override
    public float getXOffset(float x) {
        return 267.0F;
    }

    @Override
    public float getYOffset(float y) {
        return 58.0F;
    }
}
